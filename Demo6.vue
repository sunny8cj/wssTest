<template>
<div id="web-body">
  <div id="web-header">
    <el-row >
      <el-col :span="8" >
      <el-button  @click="click()" id="bt_cd"></el-button>
          <el-select  v-model="value"   @change="changeSelect()" id="select_qx" style="width:199px;height:60px">
            <el-option
              v-for="(item,index) in options"
              :key="index"
              :label="item.label"
              :value="item.value"
             ></el-option>
          </el-select>
          <el-button  @click="changeSelect()" id="bt_qx"></el-button>
      </el-col>
      <el-col :span="5" :offset="1">
<el-button   @click="change2" id="bt_web"></el-button>
<el-button @click="change1" id="bt_map"></el-button>
      </el-col>
       <el-col :span="4" :offset="1">
        <el-button  type="primary" @click="zoomOut" id="bt_small"></el-button>
        <el-button type="primary"  @click="zoomIn" id="bt_big"></el-button>
      </el-col>
    </el-row>
  </div>
  <div id="web-main">

        <div id="bt_group">
          <div id="bt_detail">
            <el-button  @click="getPbData(flines)" id="bt_gdfw"></el-button>
            <el-button  @click="showFZL(dist,'负载率')" id="bt_fzl"></el-button>
            <el-button  @click="showFZL(dist,'户均容量')"  id="bt_hjrl"></el-button>
            <el-button @click="showDetail(pId,pName)"  id="bt_sbxx"></el-button>
            <el-button  @click="hideDetail()" id="bt_back"></el-button>

            <el-select  id="select1" v-model="value1" filterable  placeholder="搜索设备"  clearable   remote    default-first-option  :loading="loading"
              :remote-method="remoteMethod"    @change="search1" >
              <el-option
                v-for="(item,index)  in options1"
                :key="item.id"
                :label="item.name"
                :value="item.name" >
              </el-option>
            </el-select>
              <el-select id="select2" v-model="value2" filterable  placeholder="搜索线路"  clearable   remote    default-first-option  :loading="loading"
              :remote-method="remoteMethod2"   @change="search2"   >
              <el-option
                v-for="(item,index)  in options2"
                :key="item"
                :label="item"
                :value="item" >
              </el-option>
            </el-select>

          </div>
          <el-button @click="closeDG" style="float:right" id="small_dg"></el-button>
         <br>
          <div id="line_name">xx单线图</div>
        </div>
          <br>
        <div id="map_control">
          <el-button id="bt_zd" :type="zd_bg" size="small" style="width:100px" @click="showInfo(1)">自动</el-button>
          <el-button  type="success" size="small" style="width:100px" @click="showLabel()">显示label</el-button>
          <br>
          <el-checkbox-group v-model="checkbox1" size="small" style="margin-top:10px" @change="showInfo(2)">
          <el-checkbox-button label="500" style="width:70px">500kv</el-checkbox-button>
          <el-checkbox-button label="220" style="width:70px">220kv</el-checkbox-button>
          <el-checkbox-button label="110" style="width:70px">110kv</el-checkbox-button>
          <el-checkbox-button label="35" style="width:60px">35kv</el-checkbox-button>
          <br>
          <el-checkbox-button label="开闭站" style="width:70px">开闭站</el-checkbox-button>
          <el-checkbox-button label="环网柜" style="width:70px">环网柜</el-checkbox-button>
          <el-checkbox-button label="配变" style="width:60px">配变</el-checkbox-button>
          </el-checkbox-group>
         </div>
      <div id="graphContainer">
     
      </div>
        <div id="map">
      </div>
      <div id="img">
        <img :src="imgurl">
      </div>

        <div id="properties">
          <div id="properPB"  style="display:none">
            型号：{{pb_xh}}
            <br>
            线损:{{pb_xs}}
            <br>
            供电户数:{{pb_gdhs}}
            <br>
            <el-button @click="dialogVisible1 =true">饼状图</el-button>
            <br>
            <el-button @click="dialogVisible2 =true">曲线图</el-button>
              <el-dialog title="饼状图数据展示" :visible.sync = "dialogVisible1" width="800px" center>
                <el-row>
                  <el-col :span="16">
                    <div id="mychart1" style="width:580px; ">
                      <ve-pie :data="chartData"  :tooltip-visible="true" :legend-visible="true" ></ve-pie>
                    </div>
                  </el-col>
                </el-row>
                <span slot="footer" class="dialog-footer">
                  <el-button @click="dialogVisible1 = false">取 消</el-button>
                  <el-button type="primary" @click="dialogVisible1 = false">确 定</el-button>
                </span>
              </el-dialog>
                
              <el-dialog title="曲线图数据展示" :visible.sync = "dialogVisible2" width="800px" center>
                <el-row>
                  <el-col :span="16">
                    <div id="mychart2" style="width:580px; ">
                      <ve-line :data="chartData1" :settings="chartSettings"> </ve-line>
                    </div>
                  </el-col>
                </el-row>
              
                
                <span slot="footer" class="dialog-footer">
                  <el-button @click="dialogVisible2 = false">取 消</el-button>
                  <el-button type="primary" @click="dialogVisible2 = false">确 定</el-button>
                </span>
              </el-dialog>
          </div>
          <div id="properXL" style="display:none">
            <div class="biaoqian">
              <div class="canshu"> 线路名称：{{xl.feederName}} </div>
            </div>
            <div class="biaoqian">
               <div class="canshu">   所带配变数:{{xl.dmsSectionCount}}</div>
            </div>
             <div class="biaoqian">
               <div class="canshu">  分支数:{{xl.dmsSectionCount}}</div>
            </div>
           <div class="biaoqian">
               <div class="canshu">  容量:{{xl.ratedCurrent}}</div>
            </div>
            <div class="biaoqian">
               <div class="canshu">  线路长度:{{xl.lineLength}}</div>
            </div>
           <div class="biaoqian">
               <div class="canshu">  实时负荷:{{xl.activePower}}</div>
            </div>
            <div class="biaoqian">
               <div class="canshu">  实时负载率:{{xl.currentLoading}}</div>
            </div>
            <div class="chart">
          <div class="canshu2">   <el-button type="info" @click="dialogVisible1 =true" style="width:150px">历 史 负 荷</el-button></div>
            </div>
            <div class="chart">
              <div class="canshu2"> <el-button type="info"  @click="dialogVisible2 =true" style="width:150px">历史载流量</el-button></div>
            </div>
            <div class="chart">
               <div class="canshu2"> <el-button type="info"  @click="dialogVisible3 =true" style="width:150px">历史负载率</el-button></div>
            </div>
              <el-dialog title="饼状图数据展示" :visible.sync = "dialogVisible1" width="800px" center>
                <el-row>
                  <el-col :span="16">
                    <div id="mychart1" style="width:580px; ">
                      <ve-pie :data="chartData"  :tooltip-visible="true" :legend-visible="true" ></ve-pie>
                    </div>
                  </el-col>
                </el-row>
                <span slot="footer" class="dialog-footer">
                  <el-button @click="dialogVisible1 = false">取 消</el-button>
                  <el-button type="primary" @click="dialogVisible1 = false">确 定</el-button>
                </span>
              </el-dialog>
                
              <el-dialog title="曲线图数据展示" :visible.sync = "dialogVisible2" width="800px" center>
                <el-row>
                  <el-col :span="16">
                    <div id="mychart2" style="width:580px; ">
                      <ve-line :data="chartData1" :settings="chartSettings"> </ve-line>
                    </div>
                  </el-col>
                </el-row>
                <span slot="footer" class="dialog-footer">
                  <el-button @click="dialogVisible2 = false">取 消</el-button>
                  <el-button type="primary" @click="dialogVisible2 = false">确 定</el-button>
                </span>
              </el-dialog>

              <el-dialog title="曲线图数据展示" :visible.sync = "dialogVisible3" width="800px" center>
                <el-row>
                  <el-col :span="16">
                    <div id="mychart3" style="width:580px; ">
                      <ve-line :data="chartData1" :settings="chartSettings"> </ve-line>
                    </div>
                  </el-col>
                </el-row>
                <span slot="footer" class="dialog-footer">
                  <el-button @click="dialogVisible3 = false">取 消</el-button>
                  <el-button type="primary" @click="dialogVisible3 = false">确 定</el-button>
                </span>
              </el-dialog>

          </div>
        </div>
  
  </div>
</div>
</template>

<script>
import post from '@/api'
import axios from 'axios'
export default {
  inject:['reload'], //注入依赖
  name: 'Demo5',
  data () {
    this.chartSettings = {
      area: true
    }
    return {
      xl:{
        feederName:'我是测试数据', // 线路名称
        dmsTransCount:'23', // 所带配变数
        dmsSectionCount: '4', //分支数
        ratedCurrent: '879',  //容量
        activePower: 'gag', //实时负荷
        lineLength: '122',  // 线路长度
        currentLoading:'rywy',  // 实时负载率
        currentLoadingHis: [] ,//负载率
        activePowerHis: [], //历史负荷
        ratedCurrentHis: [] // 载流量
      },
      dialogVisible1:false,
      dialogVisible2:false,
       dialogVisible3:false,
      chartData: {
        columns: ['状态', '数量'],
        rows: [
          { '状态': '设备巡视中', '数量': '10' },
          { '状态': '设备正常', '数量': '26' },
          { '状态': '设备故障', '数量': '5' }
        ]
      },
      chartData1: {
        columns: ['时段', '资产状况'],
        rows: [
          { '时段': '1', '资产状况': '120' },
          { '时段': '2', '资产状况': '100' },
          { '时段': '3', '资产状况': '80' },
          { '时段': '4', '资产状况': '90' },
          { '时段': '5', '资产状况': '76' },
          { '时段': '6', '资产状况': '93' },
          { '时段': '7', '资产状况': '60' },
          { '时段': '8', '资产状况': '75' },
          { '时段': '9', '资产状况': '40' }
        ]
      },
      pb_xh:'S9',
      pb_xs:'20',
      pb_gdhs:'300',
      input1:'',
      value1: '',
      value2: '',
      options1:[], // 模糊搜索的选项
      options2:[], // 模糊搜索的选项
      loading:false,
      checkbox1:[],
      zd_bg:'primary',
      imgurl: '', //承德市的网架图
      border:[],
      border_marker:[],
      selectedBorder:[], //区域外边界
      dist:'承德市',
      flines:["yd511"], //选中的线路
      point_wj: [],
      line_wj: [],
      point_map_bdz:[],
      line_map_bdz:[],
      point_map_dist:[],
      point_d:[],
      line_d:[],
      point_d:[],
      line_d:[],
      point_selected:null, // 选中点
      point_info:[],
      pb:[],  // 所有配变
      hb:[], // 户表
      pId:'', // 当前选中设备的id
      pName:'', // 当前选中设备的name
      selectedLine2:'', //当前选中的线路名称
      scopePoint:[], // 画供电范围的点
      allMarker:[],   // marker
      selectedPoint:[],
      selectedPoint2:[], //选中点的marker
      selectedLine:[],
      isdg: true,
      xj: { 'p': [ ], 'c': [ ] },
      wjj: [],
      xx: [],
      map: null,
      cell: null,
      link: [],
      d2: null,
      graph: null,
      img: null,
      locat: null,
      parent: null,
      options: [{
        value: '1',
        label: '营 子 区'
      }, {
        value: '2',
        label: '双 桥 区'
      }, {
        value: '3',
        label: '高 新 区'
      },
      {
        value: '4',
        label: '双 滦 区'
      }
      ],
      value: window.localStorage.getItem('select_value') || '1',
      msg:'这是测试地图信息窗口的！！！',
      client: '', //mqtt 
      topic: 'gridmap' ,// 订阅的主题,
      heatPoints:[],
      heatmapOverlay:null
    }
  },
  mounted () {
    this.init()
  },
  created: function() {
    // this.client = new Paho.MQTT.Client('127.0.0.1', 61623, 'wss_web'), // 第三个参数是clientID可以为空
    // this.client.connect({
    //   onSuccess: this.onConnect,
    //   userName: 'admin',
    //   password: 'password'
    // });
    // this.client.onConnectedLost = this.onConnectedLost;
    // this.client.onMessageArrived = this.onMessageArrived;
  },
  watch: { // 监视
    value: {
      deep: true, // 深度简述
      handler: function (value) {
        // 将value最新值保存到localstorage
        window.localStorage.setItem('select_value', value)
      }
    },
    value1: {
      deep: true, // 深度简述
      handler: function (value) {
        // 将value最新值保存到localstorage
        window.localStorage.setItem('select_value1', value)
      }
    }

  },
  methods: {
    // mqtt 相关
    // onConnectedLost : function(responseObject){  
    //   console.log("onConnectionLost:"+responseObject.errorMessage);
    // },
    // onMessageArrived : function (message) {
    //   console.log("onMessageArrived:"+message.payloadString);
    // },
    // onConnect:function onConnect() {
    //   // Once a connection has been made, make a subscription and send a message.
    //   console.log("onConnect");
    //   this.client.subscribe(this.topic);
    //   // message = new Paho.MQTT.Message("Hello");
    //   // message.destinationName = "World";
    //   // client.send(message);
    // },
    // // mqtt 消息发送
    // sendMsg(s){
    //     //  s = this.stest
    //   // 方法1
    //     // var message = new Paho.MQTT.Message("Hello")
    //     // message.destinationName = this.topic
    //     // this.client.send(message)
    //   // 方法2
    //     this.client.send(this.topic,s,0)
    // },
    // 选中线路后的相关操作
    sendLine(_line){
      this.flines=[]
      this.flines.push(_line)
      this.selectedLine2 = _line
      //右屏数据展示
    //  this.getLineData(_line)
      // 地理图画单线图
      this.getLine(_line)
      // 网架图画单线图
      // this.dist = _line
      this.getYZ(_line)
            //发送mqtt消息
      // this.msg = "{\"client\":\"left middle\",\"actionType\":\"switch_line\",\"parameters\":{\"fline\":\""+_line+"\",\"dist\":\""+this.dist+"\"}}"
      // this.sendMsg(this.msg)
    },
    showLabel(){

    },
    // 搜索
    search(){
      var reg = /[0-9]/;
      var result =reg.test(this.input1)
      // 判断输入的是否有数字，如果有为单线图，否则为变电站
      if(result){
         this.sendLine(this.input1)
      }else{
        //定位承德市的该变电站
        // this.click()
        this.getPoint_s(this.input1)// 根据名称定位点
      }
    },
        // 搜索 设备
    search1(){
      // if(this.value1 !=''){
      //    this.getPoint_s(this.value1)// 根据名称定位点
      // }
      this.options1.filter((item, index, arr) => {
          if(item.name===this.value1){
            this.onPoint(item.id)
            this.point_selected = this.options1[index]
            this.drawPoint()
          }
       }
      )
    },
         // 搜索 线路
    search2(){
      if(this.value2 !=''){
        this.sendLine(this.value2)// 根据名称定位点
      }
      

    },
    //模糊搜索 设备
    remoteMethod(query){
      if(query !== ''){
        this.loading = true
        setTimeout(() => {
          this.loading = false
          // 根据 input1的内容 搜索设备
          this.getDevice(query)
        })
        
      }
      else {
        this.options1 = []
      }
    },
    getDevice(value){
      post({
        url: 'http://127.0.0.1:8089/web/map/queryPoint',
        method: 'post',
        data: {name:value}
      }).then(response  => {
       console.log(response)
       this.options1 = response
      })
    },
       //模糊搜索 线路
    remoteMethod2(query){
      if(query !== ''){
        this.loading = true
        setTimeout(() => {
          this.loading = false
          // 根据 input1的内容 搜索设备
          this.getLine(query)
        })
        
      }
      else {
        this.options2 = []
      }
    },
     getLine(value){
      post({
        url: 'http://127.0.0.1:8089/web/map/queryFline',
        method: 'post',
        data: {fline:value}
      }).then(response  => {
       console.log(response)
       this.options2 = response
      })
    },
    //通过name 搜索变电站，并定位
    getPoint_s(value){
      post({
        url: 'http://127.0.0.1:8089/web/map/queryPoint',
        method: 'post',
        data: {name:value}
      }).then(response  => {
        this.point_selected = response[0]
        this.onPoint(this.point_selected.id)
        this.drawPoint()
      })
    },
    //通过id 获取当前选中点的信息  地理图 ，右屏的也在这里获取
    getPoint(_point){
      document.getElementById('properPB').style.display = 'block'
      post({
        url: 'http://127.0.0.1:8089/web/map/queryPoint',
        method: 'post',
        data: {id:_point}
      }).then(response  => {
        this.point_selected = response[0]
        // this.point_info.push({"name":response[0].name,"type":response[0].type,"lg":response[0].lg,"lat":response[0].lat})
        this.drawPoint()
      })
    },
    //当前选中设备点居中   地理图
    drawPoint(){
        if(this.selectedPoint2.length != 0 ){
                    for(let pp of this.selectedPoint2){
                        this.map.removeOverlay(pp)
                    }
                }
                if(this.wjj.length != 0){
                    for (let item of this.wjj) {
                        this.map.removeOverlay(item)  // 先清理地图上的所选线路
                    }
                }
                
        var p1 = new BMap.Point(this.point_selected.lg, this.point_selected.lat)
        // this.map.centerAndZoom(p1,17)
        let marker = new BMap.Marker(p1)
        this.map.addOverlay(marker)
        this.selectedPoint2.push(marker)
        if(this.point_selected.type === '配变'){
            var dd =[]
            for (let pp of this.point_selected.biao) {
              var p1  = new BMap.Point(pp.lg, pp.lat)
              dd.push(p1)
            }
                 var polygon = new BMap.Polygon(dd, {strokeColor: '#3A5FCD', strokeWeight: 1, strokeOpacity: 0.5,fillColor:'#3A5FCD' ,fillOpacity: 0.6})
                    this.map.addOverlay(polygon)
                    this.wjj.push(polygon)
          }
   
    },
    // 显示当前设备的详细接线图
    showDetail(_id,_name){
      document.getElementById('img').style.backgroundImage = 'url(' + require('@/components/images/details/'+_name+'.png') + ')'
      document.getElementById('img').style.display = 'block'
      document.getElementById('line_name').style.display = 'block'
      document.getElementById('line_name').innerHTML = _name+"接线图"
      // this.msg = "{\"client\":\"left\",\"actionType\":\"switch_point\",\"parameters\":{\"pointId\":\""+_id+"\", \"type\":\"detail\",\"dist\":\""+this.dist+"\"}}"
      // this.sendMsg(this.msg)
    },
    // 隐藏接线图
    hideDetail(){
             //1.如果当前dist是营子区，从设备详细跳转到营子区

        //1.如果当前dist为单线图，如果详情页可见，从详情页返回单线图

          //1.如果当前dist为单线图，如果没有打开详情页，从单线图返回营子区
       
      // 如果flines为空,表示
      if(this.flines.length ===0){
        // this.msg = "{\"client\":\"left\", \"actionType\":\"switch_dist\", \"parameters\":{\"dist\":\""+this.dist+"\"}}"
        // this.sendMsg(this.msg)
      }else{
        if(document.getElementById('img').style.display === 'none'){
          // this.dist = '营子区'
          this.flines = []
          this.drawDist(this.dist)
          this.getYZ(this.dist)
          // this.msg = "{\"client\":\"left middle\", \"actionType\":\"switch_dist\", \"parameters\":{\"dist\":\"营子区\"}}"
          // this.sendMsg(this.msg)
        }else{
          // this.msg = "{\"client\":\"left\",\"actionType\":\"switch_line\",\"parameters\":{\"dist\":\""+this.flines[0]+"\"}}"
          // this.sendMsg(this.msg)
            this.getYZ(this.flines[0])
            this.drawLine(this.flines[0])
        }
     
      }
      
    },
    //获取当前线路的配变数量
    getPbData(_line){
       //当前dist，当前fline
       //承德市的dist= null,
       var that = this
      post({
        url: 'http://127.0.0.1:8089/web/map/queryPoint',
        method: 'post',
        data: {flines:_line,types:["配变"]}
      }).then(response  => {
        that.pb = response
        that.drawPowerScope()
                   //发送mqtt消息
      // that.msg = "{\"client\":\"middle\",\"actionType\":\"switch_data\",\"parameters\":{\"fline\":\""+_line+"\",\"type\":\"供电范围\"}}"
      // that.sendMsg(this.msg)
      })
    },
    // 获取当前区域的负载率 或者 户均容量
    showFZL(_dist, _type){
      var that = this
      that.heatPoints=[]
      if(that.heatmapOverlay != null){
        that.heatmapOverlay.hide()
      }
      post({
        url: 'http://127.0.0.1:8089/web/map/queryPoint',
        method: 'post',
        data: {dist:_dist,types:["配变"]}
      }).then(response  => {
        response.forEach(element => {
          that.heatPoints.push({
            "lng":element.lg,
            "lat":element.lat,
            "count":300  // element.fzl   element.hjrl
          })
        });
      //发送mqtt消息
      that.heatMap()
      // that.msg = "{\"client\":\"middle\",\"actionType\":\"switch_data\",\"parameters\":{\"dist\":\""+_dist+"\",\"type\":\""+_type+"\"}}"
      // that.sendMsg(this.msg)
      })
    },
    //画热力图
    heatMap(){
      this.heatmapOverlay = new BMapLib.HeatmapOverlay({"radius":20});
      this.map.addOverlay(this.heatmapOverlay);
      this.heatmapOverlay.setDataSet({data:this.heatPoints,max:400});
      this.heatmapOverlay.show();
    },
    //画供电范围
    drawPowerScope(){
      //先清理地图上原本的供电范围
       if(this.scopePoint.length != 0){
            for (let item of this.scopePoint) {
                this.map.removeOverlay(item)  // 先清理地图上的所选线路
            }
        }
           if(this.selectedPoint2.length != 0 ){
                    for(let pp of this.selectedPoint2){
                        this.map.removeOverlay(pp)
                    }
                }
                if(this.selectedPoint.length != 0 ){
                    for(let pp of this.selectedPoint){
                        this.map.removeOverlay(pp)
                    }
                }
                if(this.selectedLine.length != 0){
                    for (let item of this.selectedLine) {
                        this.map.removeOverlay(item)
                    }
                }
                   if(this.wjj.length != 0){
                    for (let item of this.wjj) {
                        this.map.removeOverlay(item)  // 先清理地图上的所选线路
                    }
                }

      for (let po of this.pb) {
        var dd = []
        for(let pp of po.biao){
           var p1 = new BMap.Point(pp.lg, pp.lat)
           dd.push(p1)
        }
        var polygon = new BMap.Polygon(dd, {strokeColor: '#3A5FCD', strokeWeight: 1, strokeOpacity: 0.5,fillColor:'#3A5FCD' ,fillOpacity: 0.6})
        this.map.addOverlay(polygon)
        this.wjj.push(polygon)
      }
      this.map.setZoom(16)
    },
    //地图上画线段
    drawLine_s(sp,tp){
        var p1,p2
        if(this.selectedLine.length != 0){
            for (let item of this.selectedLine) {
                this.map.removeOverlay(item)  // 先清理地图上的所选线路
            }
        }
        for(let po of this.point_d){
            if(po.id === sp){
                p1 = new BMap.Point(po.lg, po.lat)
            }
            if(po.id === tp){
                p2 = new BMap.Point(po.lg, po.lat)
            }
        if(p1&&p2){
            var polygon = new BMap.Polyline([p1, p2], {strokeColor: 'blue', strokeWeight: 2, strokeOpacity: 0.5})
            this.map.addOverlay(polygon)
            this.selectedLine.push(polygon)
          }
        }
        this.map.centerAndZoom(p1,15); // 放大居中
    },
    //放大
    zoomIn () {
      this.graph.zoomIn()
      let z =this.map.getZoom()
      z = z+2
        this.map.setZoom(z)
        if(this.dist === '承德市'){
           this.showInfo(1)
        }
      // this.msg = "{\"client\":\"middle\",\"actionType\":\"switch_zoom\",\"parameters\":{\"action\":\"放大\"}}"
      // this.sendMsg(this.msg)
    },
    //缩小
    zoomOut () {
      this.graph.zoomOut()
       let z =this.map.getZoom()
      z = z-2
          this.map.setZoom(z)
      if(this.dist === '承德市'){
          this.showInfo(1)
        }
      //   this.msg = "{\"client\":\"middle\",\"actionType\":\"switch_zoom\",\"parameters\":{\"action\":\"缩小\"}}"
      // this.sendMsg(this.msg)
    },
    //获取承德市地理图数据
    getCd(){
      post({
        url: 'http://127.0.0.1:8089/web/map/queryPoint', // 地理图上的点，只要变电站的
        method: 'post',
        data: {types:['变电站']}
      }).then(response  => {
        this.point_map_bdz = response
        post({
          url: 'http://127.0.0.1:8089/web/map/queryLine',// 地理图上的线
          method: 'post',
          data: {sourceType:'变电站',targetType:'变电站'}
        }).then(response  => {
          this.line_map_bdz = response
          this.drawCD()
        })
      })
    },
    //绘制承德市地理图
    drawCD(){
      this.flines = []
      this.map.removeOverlay(this.heatmapOverlay)
      this.dist = '承德市'
           if(this.allMarker.length != 0){
                    for (let item of this.allMarker) {
                        this.map.removeOverlay(item)  // 清理地图上的市级的点线
                    }
                }
                      if(this.selectedPoint2.length != 0 ){
                    for(let pp of this.selectedPoint2){
                        this.map.removeOverlay(pp)
                    }
                }
         if(this.selectedPoint.length != 0){
                    for (let item of this.selectedPoint) {
                        this.map.removeOverlay(item)  // 先清理地图上的所选线路
                    }
                }
                if(this.selectedLine.length != 0){
                    for (let item of this.selectedLine) {
                        this.map.removeOverlay(item)  // 先清理地图上的所选线路
                    }
                }
                    if(this.wjj.length != 0){
                    for (let item of this.wjj) {
                        this.map.removeOverlay(item)  // 先清理地图上的所选线路
                    }
                }
      var that =this
      // 画点
       for (let item of this.point_map_bdz) {
            var icon = ''
        if(item.voltage=== 35){
           icon = new BMap.Icon('/static/bdmap/images/cd35.png', new BMap.Size(35, 35), { fillOpacity: 1, anchor: new BMap.Size(18, 18) })
        }else if(item.voltage=== 110){
           icon = new BMap.Icon('/static/bdmap/images/cd110.png', new BMap.Size(35, 35), { fillOpacity: 1, anchor: new BMap.Size(18, 18) })
        }else if(item.voltage=== 220){
           icon = new BMap.Icon('/static/bdmap/images/cd220.png', new BMap.Size(35, 35), { fillOpacity: 1, anchor: new BMap.Size(18, 18) })
        }else if(item.voltage === 500){
           icon = new BMap.Icon('/static/bdmap/images/cd500.png', new BMap.Size(35, 35), { fillOpacity: 1, anchor: new BMap.Size(18, 18) })
        }
        var p0 = new BMap.Point(item.lg, item.lat)
        var m0 = new BMap.Marker(p0, { icon: icon ,title:item.name})
        item["marker"]=m0
        item["isShow"]= 'true'
        this.allMarker.push(m0)
        // xx.push(p0)
        this.map.addOverlay(m0)
        var t = item.name.indexOf('变')
        var label = new BMap.Label(item.name.substring(0,t), { offset: new BMap.Size(-25, 45) })
        label.setStyle({borderColor: '#4682B4',borderWidth:'4px',fontSize:'20px',fontFamily:'黑体',borderStyle:'double',fontWeight:'bold'})

        m0.setLabel(label)
 
        m0.addEventListener('click', function (e) {
          let id = item.id
          that.onPoint(id)
                // 不同的设备类型，展示的content 可以不同，按照item.type来区分
          var content = "设备名称："+item.name+"<br>"+" 横坐标："+item.lg+"<br>" +"纵坐标："+item.lat 
          that.openInfo(item.lg, item.lat,content)  //显示选中点的相关信息
         
        })
        }
        // 画线
        var p1,p2
        for(let li of this.line_map_bdz){
          var dd = []
          var p1,p2
         var line_color,line_style,line_weight
          //查找起始点
          for (let po of this.point_map_bdz){
            if(po.id === li.source){
                  p1 = new BMap.Point(po.lg, po.lat)
                } else  if(po.id === li.target){
                  p2 = new BMap.Point(po.lg, po.lat)
                  switch(po.voltage){
                    case 220:
                    line_color = 'purple'
                    line_style = 'solid'
                    line_weight = 2
                      break
                    case 110:
                      line_color = 'red'
                      line_style = 'solid'
                      line_weight = 2
                      break
                    case 35:
                      line_color = '#0000EE'
                      line_style = 'solid'
                      line_weight = 2
                      break
                  }
                }
              }
              dd.push(p1)
          // 加入li的拐点
          for(let go of li.guai){
           var p = new BMap.Point(go.lg, go.lat)
            dd.push(p)
          }
          dd.push(p2)
          //画连线
            var polygon = new BMap.Polyline(dd, {strokeStyle: line_style, strokeColor: line_color, strokeWeight: line_weight, strokeOpacity: 1})
            this.map.addOverlay(polygon)
            this.allMarker.push(polygon)
            li["line"]=polygon
            // this.selectedLine.push(polygon)
      polygon.addEventListener('mouseover',function(e){
        var x = e.point.lng
        var y = e.point.lat
        var content="线路名称："+li.name+"<br>"+" 线路id："+li.id+"<br>" +"线路类型："+li.type      
        that.openInfo(x,y,content)
      })
      }
      this.map.centerAndZoom(new BMap.Point(117.972845,40.955677),11)
      this.showInfo(1)
    },
    //展示信息窗口
    openInfo(x,y,content){
      var opts ={
          width:200,
          height:200,
          title:'设备信息',
          enableAutoPan:false
        }
      var point =new BMap.Point(x,y)
      var infoWindow = new BMap.InfoWindow(content,opts)
      this.map.openInfoWindow(infoWindow,point)  // 在地图上加载信息窗口，（窗口的内容，窗口的位置）
       //大屏相同位置显示信息窗口
      // this.msg = "{\"client\":\"middle\", \"actionType\":\"show_InfoWindow\", \"parameters\":{\"content\":\""+content+"\",\"x\":\""+x+"\",\"y\":\""+y+"\"}}"
      // this.sendMsg(this.msg)
    },
    // 获取区县地理图数据
    getDistData(_dist){
       var that = this
       post({
              url: 'http://127.0.0.1:8089/web/map/queryPoint',
              method: 'post',
              data: {types:["变电站","开闭站","环网柜"],dist:_dist}
            }).then(response  => {
              that.point_d = response
              post({
              url: 'http://127.0.0.1:8089/web/map/queryLine',
              method: 'post',
              data: {types:["变电站","开闭站","环网柜"],dist:_dist}
            }).then(response  => {
              that.line_d = response
                that.drawDist(_dist)
            })
          })
    },
   // 绘制区县地理图，以区域放大居中
    drawDist(_dist){
      this.flines = []
      this.map.removeOverlay(this.heatmapOverlay)
      if(this.allMarker.length != 0 ){
        for(let pp of this.allMarker){
          this.map.removeOverlay(pp)
        }
      }
            if(this.selectedPoint2.length != 0 ){
                    for(let pp of this.selectedPoint2){
                        this.map.removeOverlay(pp)
                    }
                }
         if(this.selectedPoint.length != 0){
                    for (let item of this.selectedPoint) {
                        this.map.removeOverlay(item)  // 先清理地图上的所选线路
                    }
                }
                if(this.selectedLine.length != 0){
                    for (let item of this.selectedLine) {
                        this.map.removeOverlay(item)  // 先清理地图上的所选线路
                    }
                }
                   if(this.wjj.length != 0){
                    for (let item of this.wjj) {
                        this.map.removeOverlay(item)  // 先清理地图上的所选线路
                    }
                }
      var that = this
      that.onCd2(this.dist)
        var center =''
        switch(_dist){
          case "营子区":
            var center = new BMap.Point(117.663595, 40.552264)
            break
          case "双桥区":
            var center = new BMap.Point(117.949714, 40.98148)
            break
          case "双滦区":
            var center = new BMap.Point(117.801116,40.96473)
            break
          case "高新区":
            var center = new BMap.Point(118.001196,40.770489)
            break
        }
        that.map.centerAndZoom(center,15)
        
        for (let item of that.point_d) {
          if(item.dist === that.dist){
            var icon = ''
             var label = ''
             var t = ''
            if(item.type === '变电站'){
              if(item.voltage===35){
                icon = new BMap.Icon('../static/bdmap/images/cd35.png', new BMap.Size(35, 35), { fillOpacity: 1, anchor: new BMap.Size(18, 18) })
              }else if(item.voltage===110){
                icon = new BMap.Icon('../static/bdmap/images/cd110.png', new BMap.Size(35, 35), { fillOpacity: 1, anchor: new BMap.Size(18, 18) })
              }else if(item.voltage===220){
                icon = new BMap.Icon('../static/bdmap/images/cd220.png', new BMap.Size(35, 35), { fillOpacity: 1, anchor: new BMap.Size(18, 18) })
              }else if(item.voltage===500){
                icon = new BMap.Icon('../static/bdmap/images/cd500.png', new BMap.Size(35, 35), { fillOpacity: 1, anchor: new BMap.Size(18, 18) })
              }
              t = item.name.indexOf('变')
              label = new BMap.Label(item.name.substr(0,t), { offset: new BMap.Size(-20, 45) })
            }
            else {
              label = new BMap.Label(item.name, { offset: new BMap.Size(-20, 45) })
              switch(item.type){
                case '环网柜':
                  icon = new BMap.Icon('../static/bdmap/images/hwg.png', new BMap.Size(35, 35), { fillOpacity: 1, anchor: new BMap.Size(18, 18) })
                  break
                case '开闭站':
                 icon = new BMap.Icon('../static/bdmap/images/kbz.png', new BMap.Size(35, 35), { fillOpacity: 1, anchor: new BMap.Size(18, 18) })
                  break
                case '杆塔':
                 icon = ''
                  break
                default:
                  icon = new BMap.Icon('../static/bdmap/images/pb.png', new BMap.Size(39, 26), { fillOpacity: 1, anchor: new BMap.Size(20, 13) })
                  break
                  }
            }
            
            var p0 = new BMap.Point(item.lg, item.lat)
            var m0 = new BMap.Marker(p0, { icon: icon })
            if(item.type !== '杆塔'){
                this.map.addOverlay(m0)
                this.selectedPoint.push(m0)
                item["marker"]=m0
                item["isShow"]= 'true'
                m0.setLabel(label)
                m0.addEventListener('click', function () {
                  let id = item.id
                  that.onPoint(id)
                  // 不同的设备类型，展示的content 可以不同，按照item.type来区分
                  var content = "设备名称："+item.name+"<br>"+" 横坐标："+item.lg+"<br>" +"纵坐标："+item.lat 
                  that.openInfo(item.lg, item.lat,content)  //显示选中点的相关信息
              })
            }
          
        
        }
      }
      //画区域的线
      var p1,p2
          var line_color,line_style,line_weight
          for(let li of this.line_d){
              for(let po of this.point_d){
                if(po.id === li.source){
                  p1 = new BMap.Point(po.lg, po.lat)
              } else  if(po.id === li.target){
                  p2 = new BMap.Point(po.lg, po.lat)
                if(po.type==='变电站'){
                  switch(po.voltage){
                  case 220:
                  line_color = 'purple'
                  line_style = 'solid'
                  line_weight = 2
                    break
                  case 110:
                    line_color = 'red'
                    line_style = 'solid'
                    line_weight = 2
                    break
                  case 35:
                    line_color = '#0000EE'
                    line_style = 'solid'
                    line_weight = 2
                    break
                  }
                }else{
                    line_color = '#080808'
                    line_style = 'solid'
                    line_weight = 2
                }
                  }
              }
              
                  var polygon = new BMap.Polyline([p1, p2], {strokeStyle: line_style, strokeColor: line_color, strokeWeight: line_weight, strokeOpacity: 1})
                  this.map.addOverlay(polygon)
                  li["line"]=polygon
                  this.selectedLine.push(polygon)
        polygon.addEventListener('mouseover',function(e){
          var x = e.point.lng
          var y = e.point.lat
          var content="线路名称："+li.name+"<br>"+" 线路id："+li.id+"<br>" +"线路类型："+li.type      
          that.openInfo(x,y,content)
        })
              

          }
          this.showInfo(1)
    },
    // 获取线路坐标和连线
    getLine(_line){
      var that = this
      post({
            url: 'http://127.0.0.1:8089/web/map/queryPoint',
            method: 'post',
            data: {flines:[_line]}
          }).then(response  => {
            that.point_l = response
            post({
            url: 'http://127.0.0.1:8089/web/map/queryLine',
            method: 'post',
            data: {fline:_line}
          }).then(response  => {
            that.line_l = response
              that.drawLine(_line)
          })
        })
    },
  // 获取线路数据 
    getLineData(_line){
      var that = this
      post({
            url: 'http://127.0.0.1:8089/index/getLineInfo',
            method: 'post',
            data: {fline:_line}
          }).then(response  => {
            console.log(response) 
        })
    },
   //绘制线路
    drawLine(_line){
      if(this.selectedPoint.length != 0 ){
                  for(let pp of this.selectedPoint){
                      this.map.removeOverlay(pp)
                  }
              }
                  if(this.selectedPoint2.length != 0 ){
                  for(let pp of this.selectedPoint2){
                      this.map.removeOverlay(pp)
                  }
              }
              if(this.selectedLine.length != 0){
                  for (let item of this.selectedLine) {
                      this.map.removeOverlay(item)
                  }
              }
                     if(this.allMarker.length != 0){
                  for (let item of this.allMarker) {
                      this.map.removeOverlay(item)
                  }
              }
                  if(this.wjj.length != 0){
                  for (let item of this.wjj) {
                      this.map.removeOverlay(item)
                  }
              }
              //1. 画出该线路上的所有点 包括配变、开闭站、环网柜、杆塔
              for(let po of this.point_l){
                  var icon = ''
                  var label = new BMap.Label(po.name, { offset: new BMap.Size(-20, 45) })
                  var t = ""
                  if(po.type === '变电站'){
                      if(po.voltage=== 35){
                          icon = new BMap.Icon('/static/bdmap/images/cd35.png', new BMap.Size(35, 35), { fillOpacity: 1, anchor: new BMap.Size(18, 18) })
                      }else if(po.voltage===110){
                          icon = new BMap.Icon('/static/bdmap/images/cd110.png', new BMap.Size(35, 35), { fillOpacity: 1, anchor: new BMap.Size(18, 18) })
                      }else if(po.voltage=== 220){
                          icon = new BMap.Icon('/static/bdmap/images/cd220.png', new BMap.Size(35, 35), { fillOpacity: 1, anchor: new BMap.Size(18, 18) })
                      }
                      t = po.name.indexOf('变')
                      label = new BMap.Label(po.name.substr(0,t), { offset: new BMap.Size(-20, 45) })
                  }  else {
                      switch(po.type){
                        case '环网柜':
                          icon = new BMap.Icon('/static/bdmap/images/hwg.png', new BMap.Size(35, 35), { fillOpacity: 1, anchor: new BMap.Size(18, 18) })
                          break
                        case '开闭站':
                        icon = new BMap.Icon('/static/bdmap/images/kbz.png', new BMap.Size(35, 35), { fillOpacity: 1, anchor: new BMap.Size(18, 18) })
                          break
                        case '杆塔':
                        icon = ''
                          break
                        case '跌落式熔断器':
                        icon = new BMap.Icon('/static/bdmap/images/rdq.png', new BMap.Size(35, 35), { fillOpacity: 1, anchor: new BMap.Size(18, 18) })
                          break
                        case '配电室':
                        icon = new BMap.Icon('/static/bdmap/images/pd.png', new BMap.Size(35, 35), { fillOpacity: 1, anchor: new BMap.Size(18, 18) })
                          break
                        case '电缆分接箱':
                        icon = new BMap.Icon('/static/bdmap/images/df.png', new BMap.Size(35, 35), { fillOpacity: 1, anchor: new BMap.Size(18, 18) })
                          break
                        case '箱式变电站':
                        icon = new BMap.Icon('/static/bdmap/images/xb.png', new BMap.Size(35, 35), { fillOpacity: 1, anchor: new BMap.Size(18, 18) })
                          break
                        default:
                          icon = new BMap.Icon('/static/bdmap/images/pb.png', new BMap.Size(39, 26), { fillOpacity: 1, anchor: new BMap.Size(20, 13) })
                          break
                          }
                    }

                  var p0 = new BMap.Point(po.lg, po.lat)
                  var m0 = new BMap.Marker(p0, { icon: icon })
                  if(po.type !== '杆塔'){
                      this.map.addOverlay(m0)
                      po["marker"]=m0
                   
                  }
                     po["isShow"]= 'true'
                  this.selectedPoint.push(m0)
                  m0.setLabel(label)
                  var that =this
                  m0.addEventListener('click',function() {
                    for (let item of that.wjj) {
                      that.map.removeOverlay(item)
                    }
                  let id = po.id
                  that.onPoint(id)
                  // 不同的设备类型，展示的content 可以不同，按照item.type来区分
                  var content = "设备名称："+po.name+"<br>"+" 横坐标："+po.lg+"<br>" +"纵坐标："+po.lat 
                  that.openInfo(po.lg, po.lat,content)  //显示选中点的相关信息
                    // 如果该点type为配变，添加供电区域
                  if(po.type==='配变'){
                      // 画户表连线
                      // 添加供电区域
                      var dd =[]
                    for (let pp of po.biao) {
                      var p1  = new BMap.Point(pp.lg, pp.lat)
                      dd.push(p1)
                    }
                    var polygon = new BMap.Polygon(dd, {strokeColor: '#3A5FCD', strokeWeight: 1, strokeOpacity: 0.5,fillColor:'#3A5FCD' ,fillOpacity: 0.6 })
                    that.map.addOverlay(polygon)
                    that.wjj.push(polygon)
                
                  }
                })
                // m0.addEventListener('mouseout',function() {
                //   if(that.wjj.length != 0){
                //     for (let item of that.wjj) {
                //         that.map.removeOverlay(item)
                //     }
                //   }
                // })
              }

              // 2. 画出该线路的连线
              var p1,p2

              for (let li of this.line_l) {
                      for(let po of this.point_l){
                          if(po.id === li.source){
                              p1 = new BMap.Point(po.lg, po.lat)
                          }
                          if(po.id === li.target){
                              p2 = new BMap.Point(po.lg, po.lat)
                          }
                      }

                      var polygon = new BMap.Polyline([p1, p2], {strokeColor: '#080808', strokeWeight: 2, strokeOpacity: 1})
                      this.map.addOverlay(polygon)
                        li["line"]=polygon
                      this.selectedLine.push(polygon)
                         polygon.addEventListener('mouseover',function(e){
        var x = e.point.lng
        var y = e.point.lat
        var content="线路名称："+li.name+"<br>"+" 线路id："+li.id+"<br>" +"线路类型："+li.type      
        that.openInfo(x,y,content)
      })

              }
              this.map.centerAndZoom(p1,16); // 放大居中
    },
  // 地理图为主窗口
    change1 () {
      document.getElementById('bt_map').style.backgroundImage= 'url(' + require('@/components/images/bt_map2.png') + ')'
      document.getElementById('line_name').style.display = 'none'
      document.getElementById('bt_web').style.backgroundImage= 'url(' + require('@/components/images/bt_web.png') + ')'
      document.getElementById('map_control').style.display = 'block'

      document.getElementById('map').style.top='173px'
      document.getElementById('map').style.left='107px'
      document.getElementById('map').style.width='1376px'
      document.getElementById('map').style.height='864px'
      document.getElementById('map').style.zIndex=2

      document.getElementById('graphContainer').style.top='736px'
      document.getElementById('graphContainer').style.left='1082px'
      document.getElementById('graphContainer').style.width='400px'
      document.getElementById('graphContainer').style.height='300px'
      document.getElementById('graphContainer').style.zIndex=3
      this.d2=document.getElementById('graphContainer')
      },
    // 网架图为主窗口
    change2 () {
      document.getElementById('bt_map').style.backgroundImage= 'url(' + require('@/components/images/bt_map.png') + ')'
      document.getElementById('line_name').style.display = 'none'
      document.getElementById('bt_web').style.backgroundImage= 'url(' + require('@/components/images/bt_web2.png') + ')'
      document.getElementById('map_control').style.display = 'none'
      document.getElementById('graphContainer').style.top='173px'
      document.getElementById('graphContainer').style.left='107px'
      document.getElementById('graphContainer').style.width='1376px'
      document.getElementById('graphContainer').style.height='864px'
      document.getElementById('graphContainer').style.zIndex=2

      document.getElementById('map').style.top='736px'
      document.getElementById('map').style.left='1082px'
      document.getElementById('map').style.width='400px'
      document.getElementById('map').style.height='300px'
      document.getElementById('map').style.zIndex=3
      this.d2=document.getElementById('map')
    },
    //关闭小窗口
    closeDG () {
      this.isdg = !this.isdg
      if (this.isdg) {
        this.d2.style.display = 'block'
      } else {
        this.d2.style.display = 'none'
      }
    },
    showInfo (e) {
      var z = this.map.getZoom()
      var point =[]
      var line =[]
      // 根据当前层级判断point的值
      if(this.flines.length !=0){
          point = this.point_l
           line = this.line_l
      }else{
        if(this.dist ==='承德市'){
          point = this.point_map_bdz
          line = this.line_map_bdz
        }else {
          point = this.point_d
          line = this.line_d
        }
      }
      // 自动
      if(e ===1){
         this.zd_bg = 'primary'
        this.checkbox1 = []
        //  this.msg = "{\"client\":\"middle\", \"actionType\":\"switch_showinfo\", \"parameters\":{\"type\":\"自动\"}}"
        // this.sendMsg(this.msg)
      // alert(z)   // 11  13 15
        for (let po of point){
          this.map.removeOverlay(po.marker)
          switch(po.voltage){
            case 35:
              if (z >14) {this.map.addOverlay(po.marker) }
                break
            case 110:
              if (z>12) {this.map.addOverlay(po.marker) }
              break
            case 220:
              if (z>10) { this.map.addOverlay(po.marker) }
              break
            case 500:
              if (z >10) { this.map.addOverlay(po.marker) }
              break
          }
           switch(po.type){
            case '开闭站':
                if (z >14) {this.map.addOverlay(po.marker) }
                break
            case '环网柜':
              if (z>14) {this.map.addOverlay(po.marker) }
              break
            case '配变':
              if (z>14) { this.map.addOverlay(po.marker) }
              break
            case '杆塔':
              if (z >14) { this.map.addOverlay(po.marker) }
              break
          }
        }
        for (let li of line){
          this.map.removeOverlay(li.line)
          switch(li.fline){
            case '220-220':
                if (z>10) {this.map.addOverlay(li.line) }
                break
            case '220-110':
              if (z>12) {this.map.addOverlay(li.line) }
              break
            case '110-110':
              if (z>12) { this.map.addOverlay(li.line) }
              break
            case '110-35':
              if (z >14) {  this.map.addOverlay(li.line) }
              break
            default:   // 其他都是单线图
              if (z >14) {  this.map.addOverlay(li.line) }
              break
          }
        }
      }
      // 多选
      if(e === 2){
        // this.msg = "{\"client\":\"middle\", \"actionType\":\"switch_showinfo\", \"parameters\":{\"type\":\"多选\",\"checkbox\":\""+this.checkbox1+"\"}}"
        // this.sendMsg(this.msg)
        // 反向选择 ，如果checkbox1不包含哪个，地图就把哪个删掉
        this.zd_bg = ''
        for (let po of point){
          this.map.removeOverlay(po.marker)
          po.isShow = 'false'
          if(po.type === '变电站'){
            switch (po.voltage) {
              case 35:
                if (this.checkbox1.indexOf('35') >= 0) {  
                  this.map.addOverlay(po.marker)
                  po.isShow='true'
                  }
                break
              case 110:
                if (this.checkbox1.indexOf('110') >= 0) {  
                 this.map.addOverlay(po.marker)
                  po.isShow='true'
                  }
                break
              case 220:
                if (this.checkbox1.indexOf('220') >= 0) {
                     this.map.addOverlay(po.marker)
                  po.isShow='true'
                  }
                break
              case 500:
                if (this.checkbox1.indexOf('500') >= 0) {
                   this.map.addOverlay(po.marker)
                  po.isShow='true'
                  }
                break
            }
          }else {
            if(po.type ==='配变'){
               if (this.checkbox1.indexOf('配变') >= 0) {
                   this.map.addOverlay(po.marker)
                   po.isShow='true'
                  }
            }else if(po.type ==='开闭站'){
               if (this.checkbox1.indexOf('开闭站') >= 0) {
                    this.map.addOverlay(po.marker)
                  po.isShow='true'
                  }
            }else if(po.type ==='环网柜'){
               if (this.checkbox1.indexOf('环网柜') >= 0) {
                    this.map.addOverlay(po.marker)
                  po.isShow='true'
                  }
            }else if(po.type ==='杆塔'){
                  po.isShow='true'
            }
          }
     
        }
        for (let li of line){
          this.map.removeOverlay(li.line)
          // 如果checkbox1包括source.voltage target.volget， 则这条线出现
          var p1,p2
          for(let po of point){
               if(po.id === li.source){
                p1 = po
              }else if(po.id === li.target){
                p2 = po
              }
          }
          
          if((p1.isShow === 'true')&&(p2.isShow === 'true')){
              this.map.addOverlay(li.line)
          }
          
        }
      }
      
  
    },
    // 初始化
    init () {
        post({
          url: 'http://127.0.0.1:8089/web/wj/queryPoint', // web网架图上的点
          method: 'post',
          data: {dist:this.dist}
        }).then(response  => {
          this.point_wj = response
          post({
              url: 'http://127.0.0.1:8089/web/wj/queryLine', // web网架图上的线
              method: 'post',
              data: {dist:this.dist}
            }).then(response  => {
              this.line_wj = response
              // 画网架图
              this.main(document.getElementById('graphContainer'))
            })
        })
    },
    main (container) {
      /* eslint-disable */
      if (!mxClient.isBrowserSupported()) {
        mxUtils.error('Browser is not supported!', 200, false)
      } else {
        this.chartData.rows =  [
          { '状态': '设备巡视中', '数量': '10' },
          { '状态': '设备正常', '数量': '26' },
          { '状态': '设备故障', '数量': '5' },
           { '状态': '我是测试的', '数量': '5' }
        ]
      // 画地图
        let map = new BMap.Map('map')
        this.map = map
        map.setCurrentCity('承德')
        map.enableScrollWheelZoom() // 允许鼠标缩放
        map.centerAndZoom(new BMap.Point(117.972845,40.955677),10)
        this.getCd() // 画承德市地理图
        this.onCd1()

        // 画网架图
        this.graph = new mxGraph(container)
        this.graph.setEnabled(true) // 图元是否可移动
        this.graph.setAutoSizeCells(true)
        
        this.graph.panningHandler.useLeftButtonForPanning = true
        this.graph.setPanning(true)
     var that =this
        // 容器大小自适应
        // this.graph.setResizeContainer(true);

				// // 网架图缩放
				mxEvent.addMouseWheelListener(function(evt, up) { 
				if (up) { that.graph.zoomIn() } 
				else { that.graph.zoomOut() }
        mxEvent.consume(evt) },{ passive: false })

        this.graph.centerZoom = true;
   
        let d1= document.getElementById('graphContainer')
        that.d2= document.getElementById('map')

        //图元选中事件
        this.graph.addListener(mxEvent.CLICK, function(sender, evt) { 
          that.cell = evt.getProperty('cell')
          if(typeof that.cell != 'object') return
          let properDiv= document.getElementById('properties')
          // 点击图元，联动mqtt发消息
          // 选中的是线段
          if(that.cell.source!=null){
            // properDiv.innerHTML= that.cell.source.id+ '   '+that.cell.target.id
            // 线段联动
            // 获取线段的起始点坐标
            document.getElementById('properXL').style.display = 'block'
            document.getElementById('properPB').style.display = 'none'
            that.onLine(that.cell.source.id,that.cell.target.id) //发送起始点 给中左
            if(that.cell.value != null){
                that.sendLine(that.cell.value) //根据单线图名称画 网架图和地理图，并发送消息  给中屏画单线图
            }else{
              that.drawLine_s(that.cell.source.id,that.cell.target.id)
            }
          }else {   // 选中的设备点
            // properDiv.innerHTML=that.cell.id
            // 点联动
             document.getElementById('properXL').style.display = 'none'
            document.getElementById('properPB').style.display = 'block'
            that.pId = that.cell.id
            that.pName = that.cell.value
            that.onPoint(that.pId)
            that.getPoint(that.pId)
          }

        })
        
        let parent = this.graph.getDefaultParent()
        let style = []  
        style[mxConstants.STYLE_STROKECOLOR] = '#6482B9'  
        style[mxConstants.STYLE_EDGE] = mxEdgeStyle.ElbowConnector   // 自动拐弯的连线
        style[mxConstants.STYLE_FONTSIZE] = '18'  
				this.graph.getStylesheet().putDefaultEdgeStyle(style) 
        // style = this.graph.getStylesheet().getDefaultEdgeStyle();
        // delete style['endArrow'];
        this.graph.getModel().beginUpdate()
        try {
          this.click()
        } finally {
          this.graph.getModel().endUpdate()
        }
        // 鼠标经过时显示绿色边框
        let track = new mxCellTracker(this.graph, '#00FFff')
      }

    },
    // 获取地市、区县网架图数据
    getYZ(_dist){
      post({
        url: 'http://192.168.0.109:8089/web/wj/queryPoint',
        method: 'post',
        data: { dist:_dist}
      }).then(response => {
        this.point_wj =response
        console.log(this.point_wj)
      post({
        url: 'http://192.168.0.109:8089/web/wj/queryLine',
        method: 'post',
        data: {  dist:_dist}
      }).then(response => {
        this.line_wj=response
         this.drawYZ()
      }).catch(error => {
        console.log(error)
      })
      }).catch(error => {
        console.log(error)
      })
      
    },
    // 绘制地市、区县网架图
    drawYZ(){
      // 清空画板上的所有元素
      var that =this
      that.graph.removeCells(that.graph.getChildVertices(that.graph.getDefaultParent()))

      document.getElementById('graphContainer').style.display = 'block'
      document.getElementById('map').style.display = 'block'
      document.getElementById('img').style.display = 'none'
      document.getElementById('line_name').style.display = 'none'
      if(that.flines.length!=0){
        document.getElementById('line_name').innerHTML = that.flines[0]+'线路单线图'
        document.getElementById('line_name').style.display = 'block'
      }
      for(let item of that.point_wj){
          var labelWidth =item.labelWidth
          var labelHeight = item.labelHeight
          if(item.type === '变电站'){
            switch(item.voltage){
              case 220:
                that.img='shape=image;image=/static/mx/wss-img/220.png;'
                break
              case 110:
                that.img='shape=image;image=/static/mx/wss-img/110.png;'
                if(that.dist === '承德市'){
                  labelWidth = 40
                  labelHeight = 20
                }else{
                  labelWidth = 150
                  labelHeight = 66.9
                 }
                break
              case 35:
                that.img='shape=image;image=/static/mx/wss-img/35.png;'
                break
            }
          }
          else {
            switch(item.type){
            case '环网柜':
              that.img='shape=image;image=/static/mx/wss-img/hwg.png;'
              break
            case '开闭站-公用':
              that.img='shape=image;image=/static/mx/wss-img/10public.png;'
              break
            case '开闭站-用户':
              that.img='shape=image;image=/static/mx/wss-img/10user.png;'
              break
            case '配变':
              that.img='shape=image;image=/static/mx/wss-img/pb.png;'
              break
            case '跌落式熔断器':
              that.img='shape=image;image=/static/mx/wss-img/rdq.png;'
              break
            case '箱式变电站':
              that.img='shape=image;image=/static/mx/wss-img/xb.png;'
              break
            case '电缆分接箱':
              that.img='shape=image;image=/static/mx/wss-img/df.png;'
              break
            case '配电室':
              that.img='shape=image;image=/static/mx/wss-img/pd.png;'
              break
            default:
                that.img='shape=image;image=/static/mx/wss-img/gt.png;'
              break
            }
          }
         
        switch(item.position){
          case 'top':
            that.locat='verticalLabelPosition=top;verticalAlign=bottom'
            break
          case 'bottom':
            that.locat='verticalLabelPosition=bottom;verticalAlign=top'
            break
          case 'right':
            that.locat='labelPosition=right;align=left'
            break
          case 'left':
            that.locat='labelPosition=left;align=right'
            break
          default :
            that.locat='labelPosition=right;align=left'
            break
        }
        // if(item.type === '变电站'){
        //   that.locat='labelPosition=right;align=left'
        // }
        
        // 画模块  设备  mxGraph.insertVertex(parent，id，value，x，y，width，height，style )
        let p = that.graph.insertVertex(that.parent, item.id, item.name,item.x-0.5*labelWidth,item.y-0.5*labelHeight,labelWidth,labelHeight,this.img+this.locat)
        item["point_s"] =p
        
      }
     var p1,p2
     var count =1
      // 画网架图连线
      for(let li of that.line_wj){
          for (let po of that.point_wj){
            if(po.id === li.source) {
              p1 = po
            }else if(po.id === li.target){
              p2 = po
            }
          }
          if(p1 && p2){
            let color ='strokeColor=#000000'
            if(p2.type === '变电站'){
              switch(p2.voltage){
                case 220:
                  color='strokeColor=#ff0000'
                  break
                case 110:
                  color='strokeColor=#ff0000'
                  break
                case 35:
                  color='strokeColor=#ff8c00'
                  break
              }
            }
            else {
              switch(p2.type){
                case '环网柜':
                  color ='strokeColor=#757575'
                  break
                case '开闭站-公用':
                  color='strokeColor=#757575'
                  break
                case '开闭站-用户':
                  color='strokeColor=	#79CDCD'
                  break
              }
            }
           // 画连线   mxGraph.insertEdge(parent, id, value, source, target, style) 
            let el = that.graph.insertEdge(that.parent, li.id, li.fline, p1.point_s, p2.point_s, color)
            for (let guai of li.guai) {
              if(guai !=null){
                el.geometry.points = [new mxPoint(guai.x, guai.y)]
              }
            }
          }
      }
    },
    // 点击【承德市】按钮
    click(){
      this.dist ='承德市'
      this.getYZ(this.dist) //画承德市网架图
      this.flines =[]


      document.getElementById('bt_cd').style.backgroundImage= 'url(' + require('@/components/images/bt_cd2.png') + ')'
      document.getElementById('bt_qx').style.backgroundImage= 'url(' + require('@/components/images/bt_qx.png') + ')'

      document.getElementById('bt_gdfw').stylebackgroundImage= 'url(' + require('@/components/images/gd2.png') + ')'
      document.getElementById('bt_fzl').style.backgroundImage= 'url(' + require('@/components/images/fz.png') + ')'
      document.getElementById('bt_hjrl').style.backgroundImage= 'url(' + require('@/components/images/hj.png') + ')'
      document.getElementById('bt_sbxx').style.backgroundImage= 'url(' + require('@/components/images/sb2.png') + ')'
      document.getElementById('bt_back').style.backgroundImage= 'url(' + require('@/components/images/back2.png') + ')'
      this.getCd()  //画承德市地理图
      // this.msg = "{\"client\":\"left middle\", \"actionType\":\"switch_dist\", \"parameters\":{\"dist\":\""+this.dist+"\"}}"
      // this.sendMsg(this.msg)
   },
    // 点击【区县】按钮
    changeSelect(){
     var that =this
     that.flines =[]
      document.getElementById('bt_cd').style.backgroundImage= 'url(' + require('@/components/images/bt_cd.png') + ')'
      document.getElementById('bt_qx').style.backgroundImage= 'url(' + require('@/components/images/bt_qx2.png') + ')'

      document.getElementById('bt_gdfw').stylebackgroundImage= 'url(' + require('@/components/images/gd2.png') + ')'
      document.getElementById('bt_fzl').style.backgroundImage= 'url(' + require('@/components/images/fz.png') + ')'
      document.getElementById('bt_hjrl').style.backgroundImage= 'url(' + require('@/components/images/hj.png') + ')'
      document.getElementById('bt_sbxx').style.backgroundImage= 'url(' + require('@/components/images/sb.png') + ')'
      document.getElementById('bt_back').style.backgroundImage= 'url(' + require('@/components/images/back.png') + ')'
      switch (that.value) {
      case '1':
      that.dist="营子区"
      break
      case '2':
      that.dist="双桥区"
      break
      case '3':
      that.dist="高新区"
      break
      case '4':
      that.dist="双滦区"
      break
      }
      that.getYZ(that.dist)    // 绘制区县的网架图
      that.getDistData(that.dist) // 绘制区县的地理图
      // that.msg = "{\"client\":\"left middle\", \"actionType\":\"switch_dist\", \"parameters\":{\"dist\":\""+this.dist+"\"}}"
      // that.sendMsg(that.msg)
   },
  //发送选中设备的命令
    onPoint(value){
      var that = this
              // mqtt 发送消息
      //  that.msg = "{\"client\":\"left middle right\",\"actionType\":\"switch_point\",\"parameters\":{\"pointId\":\""+value+"\",\"dist\":\""+that.dist+"\"}}"
      //  that.sendMsg(that.msg)
    },
    //发送选中线路的命令
    onLine(sp,tp){
        var that = this
              // mqtt 发送消息
      //  that.msg = "{\"client\":\"left middle right\",\"actionType\":\"switch_line\",\"parameters\":{\"source\":\""+sp+"\", \"target\":\""+tp+"\", \"dist\":\""+that.dist+"\"}}"
      //           // this.msg = "{\"client\":\"middle\",\"actionType\":\"switch_zoom\",\"parameters\":{\"action\":\"放大\"}}"
      //  that.sendMsg(that.msg)
    },
    //画承德市内的界限
    onCd0 (url) {
      post({
        url,
        method: 'get',
        data: { }
      }).then(response => {
        var rsBoundaries = response.d
        var ply = new BMap.Polygon(rsBoundaries, {strokeWeight: 2, strokeOpacity: 0.5, strokeColor: '#7B7B7B', fillOpacity: 0.1})
        this.map.addOverlay(ply)
      }).catch(error => {
        console.log(error)
      })
    },
    //承德市以外区域，黑色
    onCd1 (url) {
      post({
        url: 'http://127.0.0.1:8089/web/map/queryDistBoundary',
        method: 'post',
        data: {dist:"外边界" }
      }).then(response => {
            this.border = response
          var dd =[]
          for(let bb of this.border){
            var p1 = new BMap.Point(bb.lg,bb.lat)
            dd.push(p1)
          }
        var ply = new BMap.Polygon(dd, {strokeWeight: 0.01, strokeOpacity: 0.01, fillColor: '#000000', fillOpacity: 0.8})
        this.map.addOverlay(ply)
      }).catch(error => {
        console.log(error)
      })
    },
    //选中区县的边界
    onCd2 (_dist) {
      for(let bb of this.border_marker){
            this.map.removeOverlay(bb)
        }
      post({
        url: 'http://127.0.0.1:8089/web/map/queryDistBoundary',// 地理图上的线
        method: 'post',
        data: {dist:_dist}
        }).then(response  => {
          this.border = response
          var dd =[]
          for(let bb of this.border){
            var p1 = new BMap.Point(bb.lg,bb.lat)
            dd.push(p1)
          }
        var polygon = new BMap.Polygon(dd, {strokeColor:'#CD6839',strokeWeight: 1, strokeOpacity: 0.5,fillOpacity: 0.15 ,fillColor:'#CD6839' })
        this.map.addOverlay(polygon)
        this.wjj.push(polygon)
      })
    
    },
  
    
  }
}
</script>

 <style >

.body{
  touch-action: none;
}
#web-body{
  height: 1081px;
  width: 1921px;
 background:url('/static/bdmap/images/bgmain.png');
 text-decoration-color: transparent;
}
#web-header{
  padding-top:80px;
  margin-left: 50px;

}
#web-main{
  margin-top:20px;
  margin-left:10px;
}
#bt_big{
  background: url('/static/bdmap/images/big2.png');
  width: 80px;
  height: 69px;
}
#bt_small{
  background: url('/static/bdmap/images/small2.png');
  width: 80px;
  height: 69px;
}
#bt_cd{
  background: url('/static/bdmap/images/bt_cd.png');
  width: 151px;
  height: 71px;
  margin-right: 10px;

}
#select_qx{
  background: url('/static/bdmap/images/select_qx.png');
   width: 199px;
  height: 71px;
  font-size: 28px;
  font-weight: 800;
  color:#3D3D3D;
  font-family: '黑体';
  left:0px;
  position: absolute;

}
#bt_qx{
  background: url('/static/bdmap/images/bt_qx2.png');
  width: 151px;
  height: 71px;
  margin-left: 10px;
}
#bt_web{
  background: url('/static/bdmap/images/bt_web2.png');
  width: 149px;
  height: 69px;
}
#bt_map{
  background: url('/static/bdmap/images/bt_map.png');
  width: 149px;
  height: 69px;
}
#graphContainer {
  position: absolute;
  top: 173px;
  left: 107px;
  width: 1376px;
  height: 864px;
  overflow:hidden;
  cursor:default;
  background-color: white;
}
#img {
  position: absolute;
  top: 298px;
  left: 106px;
  width: 1377px;
  height: 740px;
  overflow:hidden;
  cursor:default;
  background-color:black;
  z-index: 5;
}
#map {
  position: absolute;
  top: 736px;
  left: 1082px; 
  width: 400px;
  height: 300px;
  background:url('/static/mx/wss-img/dot.gif');
  overflow:hidden;
  z-index: 1;
}
#bt_group {
   position: absolute;
  top: 32px;
  width: 1450px;
  z-index: 4; 
}
#bt_detail {
  position: absolute;
  left: 30px;
  top:150px;
  width: 1200px;
  z-index: 4; 
}

#input1{
  background:url('./images/bt_ss.png');
  width: 188px;
  height: 49px;
  top:15px;
    position: absolute;
}
#select{
  position: absolute;
  left: 30px;
  margin-top:10px;
  width: 500px;
  z-index: 4; 
}
#select1{
  background-color: cadetblue;
  font-family: '黑体';
  color: black; 
  font-size: 20px;
  width: 150px;
  margin-left: 20px;
  
}
#select2{
  background-color: cadetblue;
  font-family: '黑体';
  color: black;  
  width: 150px;
  font-size: 20px;
}
#bt_gdfw {
   background: url('./images/gd.png');
  width: 130px;
  height: 49px;
}
#bt_fzl {
    background: url('./images/fz.png');
  width: 130px;
  height: 49px;
}
#bt_hjrl {
    background: url('./images/hj.png');
  width: 130px;
  height: 49px;
}
#bt_sbxx {
    background: url('./images/sb.png');
  width: 130px;
  height: 49px;
}
#bt_back {
    background: url('./images/back.png');
  width: 80px;
  height: 49px;
}
#line_name{
    position: absolute;
  margin-top:220px;
   left: 700px;
  font-size: 50;
  font-weight: 1000;
  color:brown;
  display: none;
}
#small_dg{
     position: absolute;
 background: url('/static/bdmap/images/small_dg.png');
 margin-right: 10px;
 top:150px;
left:1300px;
 width: 159px;
 height: 59px;
}
#map_control{
  position: absolute;
 width: 300px;
 top: 280px;
  margin-left: 1180px;
  z-index:100; 
  display: none;
  background-color: transparent;
}
#properties {
  position: absolute;
  top: 200px;
  left: 1522px;
  width: 300px;
  height: 850px;
  overflow:hidden;
}
.biaoqian{
  background:cadetblue;
  height: 50px;
  color: aliceblue;
  width: 100%;
  margin-top:10px;
}
.canshu{
  padding:10px;
}
.chart{
    margin-top:10px;
    height: 60px;
}
.canshu2{
  padding-top:20px;
}

</style>
    <style>/*去除百度地图版权*/
    .anchorBL{
        display:none;
    }
    </style>
