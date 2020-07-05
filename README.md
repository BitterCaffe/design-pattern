# design-pattern
##一、责任链模式
###  1.1、拦截器链、拦截器在方法内实现即局部内部类
#### 1.1.1、使用局部内部类实现拦截器的好处是参数的访问
#### 1.1.2、使用局部内部类的不友好的一面是方法比价臃肿，可读性差
#### 1.1.3、写这种实现方式是想试验一下逃逸分析技术的使用和HotSpot
#### 1.1.4、使用局部内部类和工具类的性能差别是否明显、孰优孰劣之后做一个测试