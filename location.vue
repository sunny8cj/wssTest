<template>
  <div id="main">
    <div id="title">
      <div class="biaoqian">
        用户角色
        <el-select v-model="role" placeholder="--请选择--" id="role">
          <el-option v-for="item in roleOptions" :key="item.id"  :label="item.description"  :value="item.id"         
          ></el-option>
        </el-select>
      </div>
      <div class="biaoqian" id="prov">
        省级
        <el-select v-model="province" placeholder="--请选择--" >
          <el-option v-for="item in provinceOptions" :key="item.id"  :label="item.name"  :value="item.id"         
          ></el-option>
        </el-select>
      </div>
      <div class="biaoqian" id="comp">
        公司
        <el-select v-model="company" placeholder="--请选择--">
          <el-option v-for="item in companyOptions" :key="item.id"  :label="item.name"  :value="item.id"         
          ></el-option>
        </el-select>
      </div>
      <div class="biaoqian" id="proj">
        工程
        <el-select v-model="project" placeholder="--请选择--" @change="getPoleLocate(role,province,company,project)">
          <el-option v-for="item in projectOptions" :key="item.id"  :label="item.name"  :value="item.id"         
          ></el-option>
        </el-select>
      </div>
    </div>
    <div id="bt">
        <el-button type="primary" size="medium"  @click="drawGt" >获取 </el-button>
        <el-button type="primary" size="medium"  @click="save">保存 </el-button>
    </div>

    <div id="img">

    </div>
  </div>
</template>

<script>
import axios from 'axios'
import { endianness } from 'os';
export default {
  name:'locate',
  data() {
    return {
      roleOptions: [],
      role: null,  // 角色id
      // roleId: '',
      provinceOptions: [],
      province: null,
      // provinceId: '',
      companyOptions: [],
      company: null,
      // companyId: '',
      projectOptions: [],
      project: null,
      // projectId: '',
      poles: [],
      icon:'',
      bgimg:''

    }
  },
  mounted() {
    this.getRoles()
    this.getProvince()
    this.getCompany()
    this.getProject()
  },
  watch:{
    role(val){
      switch (val){
        case 8:
          document.getElementById('prov').style.display = 'block'
          document.getElementById('comp').style.display = 'none'
          break
        case 9:
          document.getElementById('prov').style.display = 'block'
          document.getElementById('comp').style.display = 'block'
          break
      }
    },
    poles(val){
      console.log(this.poles)
    }

  },
  methods: {
    save(){
      console.log(JSON.stringify(this.poles))
       axios.post('http://127.0.0.1:28081/linesLbs/batchSave',{
        body:  this.poles
      }).then(Response =>{
       console.log(Response.data.header.message)
      })
    },
    // 获取杆塔信息 现在是测试数据，后期改roleId 和  projectId
    getPoleLocate(_role,_prov,_comp,_proj){
      axios.post('http://127.0.0.1:28081/linesLbs/queryList',{
        body: {
          roleId:1,
          provinceId:_prov,
          companyId:_comp,
          projectId:1,
        }
      }).then(Response =>{
        this.poles =Response.data.body
        this.drawGt()
      })
      // document.getElementById('gt').style.backgroundImage = "url('./images/bg.jpg')";
    },
    // 画 杆塔起始坐标
    drawGt(){
      var str =''
      this.poles.forEach(element => {
        var  pid = element.poleId
        str = str+`<div id=${element.poleId}  class = "gtzb"
                  style="background: cadetblue; cursor: pointer; width:20px;height:20px;left:${element.x}px;top:${element.y}px;position:absolute">
              ${element.poleId}
            </div>`
        var gt0 = document.getElementById(pid);
        });
      var pa =  document.getElementById('img')
      pa.innerHTML= str
      var that = this
      // js 事件委托  https://www.jianshu.com/p/1efe1bd4fb87
      pa.addEventListener('mousedown',function(e){
        var gt =e.target 
        console.log(gt)
        while(gt.className !== 'gtzb'){
          if(gt === pa){
            gt =null
            break
          }
          gt = gt.parentNode
        }
        if(gt){
          console.log('是gtzb')
          document.onmousemove = function(e) {
            var e = e || window.event;

            gt.style.left = e.clientX -pa.offsetLeft + "px";
            gt.style.top = e.clientY - pa.offsetTop+ "px";

            /*对于大的DIV四个边界的判断*/
            if (e.clientX -pa.offsetLeft <= 0) {
                gt.style.left = 0 + "px";
            }
            if (e.clientY - pa.offsetTop <= 0) {
                gt.style.top = 0 + "px";
            }
            if (e.clientX -pa.offsetLeft>= (pa.offsetWidth-gt.offsetWidth)) {
                gt.style.left = (pa.offsetWidth-gt.offsetWidth)+ "px";
            }
            if (e.clientY - pa.offsetTop>= (pa.offsetHeight-gt.offsetHeight)) {
                gt.style.top =(pa.offsetHeight-gt.offsetHeight)+ "px";
            }
          };
          // var that = this
          document.onmouseup = function() {
            document.onmousemove = null;
            document.onmouseup = null;
            // that.poles.forEach(element => {
            //   if(element.poleId == gt.innerText){
            //     element.x = gt.style.left
            //     element.y = gt.style.top
            //   }
            // });
            console.log(gt.innerText+","+gt.style.left+","+gt.style.top)
        
            that.poles.filter(element =>{
              if(element.poleId == gt.innerText){
                element.x = gt.style.left.replace('px','')
                element.y = gt.style.top.replace('px','')
              }
            })
         };
        }
        else console.log('你点击的不是li')
      })
    },
    getRoles(){
      axios.post('http://127.0.0.1:28081/role/queryNormalList',{
        body: {}
      }).then(Response =>{
        this.roleOptions = Response.data.body
      })
    },
    getProvince(){
      axios.post('http://127.0.0.1:28081/province/queryList',{
        body: {}
      }).then(Response =>{
        this.provinceOptions = Response.data.body
      })
    },
    getCompany(){
      axios.post('http://127.0.0.1:28081/company/queryList',{
        body: {}
      }).then(Response =>{
        this.companyOptions = Response.data.body
      })
    },
    getProject(){
      axios.post('http://127.0.0.1:28081/project/queryPageList',{
        body: {}
      }).then(Response =>{
        this.projectOptions = Response.data.body.list
      })
    }
  }
  
}
</script>

<style scoped>
#title{
  width: 80%;
  height: 50px;
  margin:20px auto;
  display: flex;
}
.biaoqian{
  flex:1;
}
#prov{
  display: none;
}
#comp{
  display: none;
}
#proj{
  display: block;
}
#img{
  position: absolute;
  top:100px;
  left:20%;
  width: 800px;
  height: 500px;
  background: grey
}
#bt{
  margin-top:100px;
  
}
</style>
