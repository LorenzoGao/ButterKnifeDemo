# ButterKnifeDemo
##### 1.ButterKnife介绍
主要是决解findViewById和setOnclick 还包括资源注入和编译时注解

使用参考:ButterKnife github地址：https://github.com/JakeWharton/butterknife

##### 2.ButterKnife原理分析
主要采用编译时注解，说白了就是用apt生成代码

##### 3.自己手动实现
只要能够生成XXXActivity_ViewBinding类就好了 apt mirror(模块的知识)
thinking in java (书)

配置代码生成  注意 没改动代码的情况下 不会去生成代码

