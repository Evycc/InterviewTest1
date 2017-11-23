# InterviewTest1
已知带有特殊标记的sql语句和前端传过来的参数集合，用参数替换sql的特殊标记，如果标记对应的参数存在，则直接替换，如果不存在，要把对应的一段条件去掉，最后得出一个能用于查询的sql语句。要求给出完整代码，代码加上必要的注释。

例如:已知 1）带有特殊标记的sql语句 select * from T1 where 1=1 and a = :aa  and (b =:bb or b =:cc) and e like :ee

          2）前端传过来的参数有 aa= "111" , bb= "222", cc= "333",dd = "444"

     得到 select * from T1 where 1=1 and a = 111 and (b = 222 or b = 333)

 /**

  * 用参数替换sql的特殊标记，如果标记对应的参数存在，则直接替换，如果不存在，要把对应的一段条件去掉，最后得出一个能用于查询的sql语句，返回能用于查询的sql语句。

  * @param labelSql 带有标记的sql语句, 如select * from T1 where 1=1 and a = :aa  and (b =:bb or b =:cc) and e like :ee

  * @param param 参数集合，如aa= "111" , bb= "222", cc= "333",dd = "444"

  * @return 能用于查询的sql,如select * from T1 where 1=1 and a = 111 and (b = 222 or b = 333)

  */

 public static String getRunnableSql(String labelSql,Map<String,Object> param){

  //write your code here;

 }

完成了，请用如下情况去测试
1) sql: select * from T1 where 1=1 and (((a = :AA or B like :BB or c in :CC) and d in :DD ) or e = :EE) and f like :FF
2）参数：GG = "1223" , aa = "first"

最终得到 select * from T1 where 1=1
也测一下：有些标记有参数，有些标记没有参数，看最终结果是否正确

