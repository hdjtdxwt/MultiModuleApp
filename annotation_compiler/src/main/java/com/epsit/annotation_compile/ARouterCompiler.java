package com.epsit.annotation_compile;

import com.epsit.annotation.BuildPath;
import com.google.auto.service.AutoService;

import java.io.IOException;
import java.io.Writer;
import java.util.HashMap;
import java.util.HashSet;
import java.util.Iterator;
import java.util.Map;
import java.util.Set;

import javax.annotation.processing.AbstractProcessor;
import javax.annotation.processing.Filer;
import javax.annotation.processing.ProcessingEnvironment;
import javax.annotation.processing.Processor;
import javax.annotation.processing.RoundEnvironment;
import javax.lang.model.SourceVersion;
import javax.lang.model.element.Element;
import javax.lang.model.element.TypeElement;
import javax.tools.JavaFileObject;

//注解处理器，这个是专门处理ARouter注解的
@AutoService(Processor.class) //这里是注册的操作
public class ARouterCompiler extends AbstractProcessor {
    //文件生成器对象
    Filer filer;

    @Override
    public synchronized void init(ProcessingEnvironment processingEnv) {
        super.init(processingEnv);
        filer = processingEnv.getFiler();
    }

    @Override
    public SourceVersion getSupportedSourceVersion() {
        return processingEnv.getSourceVersion();
    }

    @Override
    public Set<String> getSupportedAnnotationTypes() {
        Set<String> types = new HashSet<>();
        types.add(BuildPath.class.getCanonicalName());
        return types;
    }

    @Override
    public boolean process(Set<? extends TypeElement> annotations, RoundEnvironment roundEnv) {
        Set<? extends Element> elementAnnotations = roundEnv.getElementsAnnotatedWith(BuildPath.class);
        Map<String,String> map = new HashMap<>();
        for (Element element: elementAnnotations ) {
            //因为BuildPath都是写在类上面的注解，所以可以直接转换成类节点
            TypeElement typeElement = (TypeElement)element;
            //类名，写在哪个类上面
            String className = typeElement.getQualifiedName().toString();
            //key
            String keyValue = typeElement.getAnnotation(BuildPath.class).value();
            map.put(keyValue, className+".class");
        }
        System.out.println("------------------------------------------size:"+map.size());
        //那就开始生成这个包下的ActivityUtil类，将key和Class对象关联绑定上
        if(map.size()>0){
            Writer writer = null;
            //这个是生成的类名，为了在合并后不会有重名类存在而加了一个时间戳
            String activityUtilName = "ActivityUtil"+System.currentTimeMillis();
            try {
                JavaFileObject sourceFile = filer.createSourceFile("com.epsit.util."+activityUtilName+";");
                writer = sourceFile.openWriter();
                //所有的ARouter的跳转的类都会生成在这个目录下，所以有根据包名获取包下所有类的需求
                writer.write("package com.epsit.util;\n");

                writer.write("\n");
                writer.write("import com.epsit.arouter.ARouter;\n");
                writer.write("import com.epsit.arouter.IRouter;\n");
                writer.write("\n");
                writer.write("public class "+activityUtilName+" implements IRouter {\n");
                writer.write("\n" +
                        "    @Override\n" +
                        "    public void putActivity() {\n");
                Iterator<String> iterator = map.keySet().iterator();
                while(iterator.hasNext()){
                    String key = iterator.next();
                    String className = map.get(key);
                    //第一个参数是String,第二个参数是Class,
                    writer.write("        ARouter.getInstance().putActivity(\""+key+"\", "+className+");\n" );
                }

                        writer.write("    }\n" +
                        "\n" +
                        "    @Override\n" +
                        "    public Class getClassByKey(String key) {\n" +
                        "        return ARouter.getInstance().getActivityByKey(key);\n" +
                        "    }\n" +
                        "}");
            } catch (Exception e) {
                e.printStackTrace();
            }finally {
                if(writer!=null){
                    try {
                        writer.close();
                    } catch (IOException e) {
                        e.printStackTrace();
                    }
                }
            }
        }else{
            System.out.println("没有数据，size=0");
        }
        return false;
    }
}
