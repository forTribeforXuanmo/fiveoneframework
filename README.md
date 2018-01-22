# fiveoneframework （五一班框架）
简易框架，实现mvc+aop+ioc+db
#自定义了@Action @Data控制层注解 @Aspect  @Inject aop+ioc注解 还有@Transaction事务注解 数据库操作使用DBUtils封装了一次，拥有框架的基本功能

```java
@Controller
public class UserController {
    @Inject
    private UserFunction userFunction;

    /**
     * 测试无参数
     * @return
     */
    @Action("get:/")
    public View goIndex(){
        return new View("index.jsp").addModel("name","lishengzhu").addModel("password","1234");
    }

    /**
     * 测试有参数
     * @param param
     * @return
     */
    @Action("get:/go2")
    public View go2(Param param){
        userFunction.sayHello();
        userFunction.sayGoodBye();
        DataBaseHelper.insertEntity(User.class,param.getParamMap());
        return new View("go2.jsp");
    }

    /**
     * 测试无参数切返回字符串对象给前台
     * @return
     */
    @Action("get:/noParamTest")
    public Data noParamTest(){
        Map map=new HashMap<>();
        map.put("a","100");
        map.put("b","200");
        Data data=new Data(map);
        return data;
    }

    /**
     * 测试无参数且返回容器对象给前台
     * @return
     */
    @Action("get:/noParamTest2")
    public Data noParamTest2(){
        Data data=new Data("aaa");
        return data;
    }

    /**
     * 测试Data注解
     * @param param
     * @return
     */
    @Action("post:/annotationTest")
    @com.lsz.fiveoneframework.annotation.Data
    public List annotationTest(Param param){
        String name=param.getString("name");
        List list=new ArrayList();
        for(int i=0;i<10;i++){
            list.add(name+i);
        }
        return list;
    }
}
```
