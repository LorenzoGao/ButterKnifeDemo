package com.butterknife.compiler;

import com.butterknife.annotations.BindView;
import com.google.auto.service.AutoService;
import com.squareup.javapoet.ClassName;
import com.squareup.javapoet.JavaFile;
import com.squareup.javapoet.MethodSpec;
import com.squareup.javapoet.TypeSpec;

import java.io.IOException;
import java.lang.annotation.Annotation;
import java.util.ArrayList;
import java.util.LinkedHashMap;
import java.util.LinkedHashSet;
import java.util.List;
import java.util.Map;
import java.util.Set;

import javax.annotation.processing.AbstractProcessor;
import javax.annotation.processing.Filer;
import javax.annotation.processing.ProcessingEnvironment;
import javax.annotation.processing.Processor;
import javax.annotation.processing.RoundEnvironment;
import javax.lang.model.SourceVersion;
import javax.lang.model.element.Element;
import javax.lang.model.element.Modifier;
import javax.lang.model.element.TypeElement;
import javax.lang.model.util.Elements;

/**
 * 作者：Lorenzo Gao
 * Date: 2018/5/16
 * Time: 11:26
 * 邮箱：2508719070@qq.com
 * Description:
 */

@AutoService(Processor.class)
public class ButterKnifeProcessor extends AbstractProcessor {

    private Filer mFiler;

    private Elements mElementsUtils;

    @Override
    public synchronized void init(ProcessingEnvironment processingEnv) {
        super.init(processingEnv);
        mFiler = processingEnv.getFiler();
        mElementsUtils = processingEnv.getElementUtils();
    }

    @Override
    public SourceVersion getSupportedSourceVersion() {
        return super.getSupportedSourceVersion();
    }

    //给到需要处理的注解
    @Override
    public Set<String> getSupportedAnnotationTypes() {
        Set<String> types = new LinkedHashSet<>();
        for (Class<? extends Annotation> annotation : getSupportedAnnotations()) {
            types.add(annotation.getCanonicalName());
        }
        return types;
    }

    /**
     * {@inheritDoc}
     *
     * @param annotations
     * @param roundEnv    指定处理的版本
     */

    @Override
    public boolean process(Set<? extends TypeElement> annotations, RoundEnvironment roundEnv) {

        // process 方法代表的是，有注解都会进来，但是这里面是一团乱麻
        Set<? extends Element> elements = roundEnv.getElementsAnnotatedWith(BindView.class);

        //解析 属性 activity->List<Element>
        Map<Element, List<Element>> elementListMap = new LinkedHashMap<>();

        for (Element element : elements) {
            Element enclosingElement = element.getEnclosingElement();
            List<Element> viewBindElement = elementListMap.get(enclosingElement);
            if (viewBindElement == null) {
                viewBindElement = new ArrayList<>();
                elementListMap.put(enclosingElement, viewBindElement);
            }
            viewBindElement.add(element);
        }


        //生成代码
        for (Map.Entry<Element, List<Element>> entry : elementListMap.entrySet()) {
            Element enclosingElement = entry.getKey();
            List<Element> viewBindElement = entry.getValue();
            String activityClassNameStr = enclosingElement.getSimpleName().toString();
            ClassName unbinderClassName = ClassName.get("com.butterknife", "Unbinder");
            ClassName activityClassName = ClassName.bestGuess(activityClassNameStr);
            TypeSpec.Builder classBuilder = TypeSpec.classBuilder(activityClassNameStr + "_ViewBinding")
                    .addModifiers(Modifier.FINAL, Modifier.PUBLIC)
                    .addSuperinterface(unbinderClassName)
                    .addField(activityClassName, "target", Modifier.PRIVATE);

            //实现Unbinder方法
            ClassName callSuperClassName = ClassName.get("android.support.annotation", "CallSuper");
            MethodSpec.Builder unBinderMethodSpec = MethodSpec.methodBuilder("unbind")
                    .addAnnotation(Override.class)
                    .addModifiers(Modifier.FINAL, Modifier.PUBLIC)
                    .addAnnotation(callSuperClassName);

            unBinderMethodSpec.addStatement("$T target=this.target", activityClassName);
            unBinderMethodSpec.addStatement("if (target == null) throw  new IllegalStateException(\"Bindings already cleard\")");


            //构造函数
            MethodSpec.Builder constructorMethodBuilder = MethodSpec.constructorBuilder()
                    .addParameter(activityClassName, "target")
                    .addModifiers(Modifier.PUBLIC);

            // this.target=target
            constructorMethodBuilder.addStatement("this.target=target");

            //findViewById
            for (Element element : viewBindElement) {
                String fildName = element.getSimpleName().toString();
                ClassName utilsClassName = ClassName.get("com.butterknife", "Utils");
                int resId = element.getAnnotation(BindView.class).value();
                constructorMethodBuilder.addStatement("target.$L=$T.findViewByid(target,$L)", fildName, utilsClassName, resId);


                unBinderMethodSpec.addStatement("target.$L=null", fildName);
            }


            classBuilder.addMethod(unBinderMethodSpec.build());
            classBuilder.addMethod(constructorMethodBuilder.build());

//生成类
            try {
                //获取包名
                String packgeName = mElementsUtils.getPackageOf(enclosingElement).getQualifiedName().toString();
                JavaFile.builder(packgeName, classBuilder.build())
                        .addFileComment("butterKnife自动生成")
                        .build().writeTo(mFiler);

            } catch (IOException e) {
                e.printStackTrace();
                System.out.println("翻车了");
            }


            // 自己写
//            FileWriter fi =null;
//            fi.write("public static class");

        }
        return false;
    }


    private Set<Class<? extends Annotation>> getSupportedAnnotations() {
        Set<Class<? extends Annotation>> annotations = new LinkedHashSet<>();
        // 需要解析的自定义注解  BindView onClick
        annotations.add(BindView.class);
        return annotations;
    }
}
