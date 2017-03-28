# Android-Fitness

2017-03-28:
	修复一个bug：饮食记录数超过一页时删除后面的记录会闪退（listview的getChildAt()获取的item只有第一页的有效，滚动条往下的item使用时报nullpointer异常，修改为先使用getAdapter后调用适配器的getView获取item）
	listview的自定义item布局增加一个隐藏textview保存记录的id（与数据表字段相同）用于删除记录时直接使用

健身饮食记录助手，自带离线食品营养数据库
![image](https://github.com/ghsxl/Android-Fitness/blob/master/Fitness.jpg)
