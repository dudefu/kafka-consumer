select * from test_vehicle
drop table test_vehicle;

create table test_vehicle
(
  appeartime timestamp without time zone, -- 车辆出现时间 ...
  brandreliability character varying(30), -- 品牌标志识别可信度...
  calling integer, -- 打电话状态...
  carofvehicle character varying(64), -- 车厢...
  descoffrontitem character varying(256), -- 车前部物品描述 ...
  descofrearitem character varying(256), -- 车后部物品描述...
  deviceid character varying(20), -- 设备编码...
  direction character varying(256), -- 行驶方向...
  disappeartime timestamp without time zone, -- 车辆消失时间...
  drivingstatuscode character varying(4), -- 行驶状态代码...
  filmcolor character varying(64), -- 贴膜颜色...
  hasplate boolean, -- 有无车牌...
  hitmarkinfo character varying(256), -- 撞痕信息...
  infokind integer, -- 信息分类...
  isaltered boolean, -- 是否涂改...
  iscovered boolean, -- 是否遮挡...
  isdecked boolean, -- 是否套牌...
  ismodified boolean, -- 改装标志...
  issuspicious boolean, -- 是否可疑车...
  laneno integer, -- 车道号...
  lefttopx integer, -- 左上角 x 坐标...
  lefttopy integer, -- 左上角 y 坐标...
  marktime timestamp without time zone, -- 位置标记时间...
  motorvehicleid character varying(48), -- 车辆标识...
  nameofpassedroad character varying(64), -- 经过道路名称...
  numofpassenger integer, -- 车内人数...
  passtime timestamp without time zone, -- 经过时刻...
  platecharreliability character varying(64), -- 每位号牌号码可信度...
  plateclass character varying(20), -- 号牌种类...
  platecolor character varying(20), -- 车牌颜色...
  platedescribe character varying(64), -- 车牌描述...
  plateno character varying(15), -- 车牌号...
  platenoattach character varying(15), -- 挂车牌号...
  platereliability character varying(30), -- 号牌识别可信度...
  rearviewmirror character varying(64), -- 后视镜...
  rightbtmx integer, -- 右下角 x 坐标...
  rightbtmy integer, -- 右下角 y 坐标...
  safetybelt integer, -- 安全带状态...
  sideofvehicle character varying(64), -- 车侧...
  sourceid character varying(41), -- 来源标识...
  speed double precision, -- 行驶速度...
  storageurl1 character varying(256), -- 近景照片...
  storageurl2 character varying(256), -- 车牌照片...
  storageurl3 character varying(256), -- 远景照片...
  storageurl4 character varying(256), -- 合成图...
  storageurl5 character varying(256), -- 缩略图...
  subimagelist text, -- 图像列表...
  sunvisor integer, -- 遮阳板状态...
  tollgateid character varying(20), -- 关联卡口编号...
  usingpropertiescode integer, -- 车辆使用性质代码...
  vehiclebodydesc character varying(128), -- 车身描述...
  vehiclebrand character varying(30), -- 车辆品牌...
  vehiclechassis character varying(64), -- 底盘...
  vehicleclass character varying(30), -- 车辆类型...
  vehiclecolor character varying(20), -- 车身颜色...
  vehiclecolordepth character varying(32), -- 颜色深浅...
  vehicledoor character varying(64), -- 车门...
  vehiclefrontitem character varying(20), -- 车前部物品...
  vehicleheight integer, -- 车辆高度...
  vehiclehood character varying(64), -- 车前盖...
  vehiclelength integer, -- 车辆长度...
  vehiclemodel character varying(32), -- 车辆型号...
  vehiclerearitem character varying(20), -- 车后部物品...
  vehicleroof character varying(64), -- 车顶...
  vehicleshielding character varying(64), -- 遮挡...
  vehiclestyles character varying(16), -- 车辆年款...
  vehicletrunk character varying(64), -- 车后盖...
  vehiclewheel character varying(64), -- 车轮...
  vehiclewidth integer, -- 车辆宽度...
  vehiclewindow character varying(64), -- 车窗...
  wheelprintegeredpattern character varying(30) -- 车轮印花纹...
)
with (appendonly=true, compresslevel=5, orientation=column,
  oids=false
)
DISTRIBUTED BY (vehiclecolor);

alter table test_vehicle4
  owner to gpadmin;
comment on column test_vehicle4.appeartime is '车辆出现时间
人工采集时有效
o';
comment on column test_vehicle4.brandreliability is '品牌标志识别可信度
车辆品牌标志可信度；以 0～100 之间数值表示百分比，数值越大可信度越高
o';
comment on column test_vehicle4.calling is '打电话状态
0：未打电话；1：打电话中
o';
comment on column test_vehicle4."carofvehicle" is '车厢
对车厢的描述
o ';
comment on column test_vehicle4."descoffrontitem" is '车前部物品描述
对车前部物品数量、颜色、种类等信息的描述
o ';
comment on column test_vehicle4."descofrearitem" is '车后部物品描述
对车后部物品数量、颜色、种类等信息的描述
o';
comment on column test_vehicle4."deviceid" is '设备编码
设备编码，自动采集必选
r/o ';
comment on column test_vehicle4.direction is '行驶方向
附：水平方向（hdirectiontype、horizontalshottype）
o ';
comment on column test_vehicle4."disappeartime" is '车辆消失时间
o ';
comment on column test_vehicle4."drivingstatuscode" is '行驶状态代码
ga/t 16.55，机动车行驶状态代码
o';
comment on column test_vehicle4."filmcolor" is '贴膜颜色
附：贴膜颜色（autofoilcolortype）
o';
comment on column test_vehicle4."hasplate" is '有无车牌
r/o ';
comment on column test_vehicle4."hitmarkinfo" is '撞痕信息
附：撞痕信息（dentinfotype）
o';
comment on column test_vehicle4."infokind" is '信息分类
附：视频图像信息类型（infotype）
r';
comment on column test_vehicle4."isaltered" is '是否涂改
o ';
comment on column test_vehicle4."iscovered" is '是否遮挡
o';
comment on column test_vehicle4."isdecked" is '是否套牌
o ';
comment on column test_vehicle4."ismodified" is '改装标志
o ';
comment on column test_vehicle4."issuspicious" is '是否可疑车
o ';
comment on column test_vehicle4."laneno" is '车道号
车辆行驶方向最左车道为 1，由左向右顺序编号
o ';
comment on column test_vehicle4."lefttopx" is '左上角 x 坐标
车的轮廓外接矩形在画面中的位置，记录左上角坐标及右下角坐标，自动采集记录时为必选
r/o ';
comment on column test_vehicle4."lefttopy" is '左上角 y 坐标
r/o';
comment on column test_vehicle4."marktime" is '位置标记时间
 时间格式统一为yyyymmddhhmmss，年月日时分秒
o';
comment on column test_vehicle4."motorvehicleid" is '车辆标识
车辆全局唯一标识
r';
comment on column test_vehicle4."nameofpassedroad" is '经过道路名称
车辆被标注时经过的道路名称
o';
comment on column test_vehicle4."numofpassenger" is '车内人数
车辆内人员数量
o ';
comment on column test_vehicle4."passtime" is '经过时刻
卡口事件有效，过车时间
o';
comment on column test_vehicle4."platecharreliability" is '每位号牌号码可信度
号牌号码的识别可信度，以 0～100 数值表示百分比，数值越大可信度越高。按“字符 1-可信度 1，字符 2-可信度 2”方式排列，中间为英文半角连接线、逗号；例如识别号牌号码为：苏 b12345，则取值为：”苏-80，b-90，1-90，2-88，3-90，4-67，5-87”
o ';
comment on column test_vehicle4."plateclass" is '号牌种类
附：号牌种类（plateclasstype）
r/o ';
comment on column test_vehicle4."platecolor" is '车牌颜色
指号牌底色，取 colortype 中部分值： 黑色，白色，黄色，蓝色，绿色 ，附：颜色（colortype）
r/o ';
comment on column test_vehicle4."platedescribe" is '车牌描述
车牌框广告信息，包括车行名称，联系电话等
o ';
comment on column test_vehicle4."plateno" is '车牌号
各类机动车号牌编号车牌全部无法识别的以“无车牌”标识，部分未识别的每个字符以半角‘-’代替
r/o ';
comment on column test_vehicle4."platenoattach" is '挂车牌号
各类机动车挂车号牌编号
o ';
comment on column test_vehicle4."platereliability" is '号牌识别可信度
整个号牌号码的识别可信度，以 0～100 数值表示百分比，数值越大可信度越高
o ';
comment on column test_vehicle4."rearviewmirror" is '后视镜
对后视镜的描述
o ';
comment on column test_vehicle4."rightbtmx" is '右下角 x 坐标
r/o ';
comment on column test_vehicle4."rightbtmy" is '右下角 y 坐标
r/o';
comment on column test_vehicle4."safetybelt" is '安全带状态
0：未系；1：有系
o';
comment on column test_vehicle4."sideofvehicle" is '车侧
对车侧面的描述，不包括门
o';
comment on column test_vehicle4."sourceid" is '来源标识
来源图像标识
r';
comment on column test_vehicle4.speed is '行驶速度
单位千米每小时（km/h）
o';
comment on column test_vehicle4."storageurl1" is '近景照片
卡口相机所拍照片，自动采集必选，图像访问路径，采用 uri 命名规则
r ';
comment on column test_vehicle4."storageurl2" is '车牌照片
o ';
comment on column test_vehicle4."storageurl3" is '远景照片
全景相机所拍照片
o ';
comment on column test_vehicle4."storageurl4" is '合成图
o ';
comment on column test_vehicle4."storageurl5" is '缩略图
o ';
comment on column test_vehicle4."subimagelist" is '图像列表
可以包含 0 个或者多个子图像对象
o';
comment on column test_vehicle4.sunvisor is '遮阳板状态
0：收起；1：放下
o ';
comment on column test_vehicle4."tollgateid" is '关联卡口编号
卡口编码
o ';
comment on column test_vehicle4."usingpropertiescode" is '车辆使用性质代码
ga/t 16.3，机动车使用性质代码
o';
comment on column test_vehicle4."vehiclebodydesc" is '车身描述
描述车身上的文字信息，或者车上载物信息
o';
comment on column test_vehicle4."vehiclebrand" is '车辆品牌
附：车辆品牌代码（vehiclebrandtype）
o ';
comment on column test_vehicle4."vehiclechassis" is '底盘
对车底盘的描述
o ';
comment on column test_vehicle4."vehicleclass" is '车辆类型
ga/t16.4 机动车车辆类型代码
o';
comment on column test_vehicle4."vehiclecolor" is '车身颜色
附：颜色（colortype）
r';
comment on column test_vehicle4."vehiclecolordepth" is '颜色深浅
附：颜色深浅（vehiclecolordepthtype）
o';
comment on column test_vehicle4."vehicledoor" is '车门
对车门的描述
o ';
comment on column test_vehicle4."vehiclefrontitem" is '车前部物品
当有多个时可用英文半角逗号分隔，附：车前部物品类型（frontthingtype）
o ';
comment on column test_vehicle4."vehicleheight" is '车辆高度
4 位整数，单位为毫米（mm）
o';
comment on column test_vehicle4."vehiclehood" is '车前盖
对车前盖的描述
o ';
comment on column test_vehicle4."vehiclelength" is '车辆长度
5 位整数，单位为毫米（mm）
o ';
comment on column test_vehicle4."vehiclemodel" is '车辆型号
o ';
comment on column test_vehicle4."vehiclerearitem" is '车后部物品
当有多个时可用英文半角逗号分隔，附：车后部物品类型（rearthingtype）
o';
comment on column test_vehicle4."vehicleroof" is '车顶
对车顶的描述
o';
comment on column test_vehicle4."vehicleshielding" is '遮挡
对车遮挡物的描述
o';
comment on column test_vehicle4."vehiclestyles" is '车辆年款
o ';
comment on column test_vehicle4."vehicletrunk" is '车后盖
对车后盖的描述
o ';
comment on column test_vehicle4."vehiclewheel" is '车轮
对车轮的描述
o';
comment on column test_vehicle4."vehiclewidth" is '车辆宽度
4 位整数，单位为毫米（mm）
o ';
comment on column test_vehicle4."vehiclewindow" is '车窗
对车窗的描述
o';
comment on column test_vehicle4."wheelprintegeredpattern" is '车轮印花纹
ga 240.43，车轮印花纹
o ';
