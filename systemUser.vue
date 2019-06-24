<template>
  <div>
    <el-button type="primary" icon="el-icon-circle-plus-outline" @click="handleCreate" style="float:right">新增用户</el-button>
     <br>
    <el-table :data="list" stripe style="width:80%; dispaly:flex;margin:20px auto">
      <el-table-column prop="id" label="序号" width="100" align="center" style="flex:1"/>
      <el-table-column prop="username" label="用户账号" width="200" align="center" style="flex:1"/>
      <el-table-column prop="fullname" label="用户姓名" width="200" align="center" style="flex:1"/>
      <el-table-column prop="roleId" label="角色" width="150" align="center" style="flex:1"/>
      <el-table-column prop="createTime" label="创建时间" width="300" align="center" style="flex:1">
      </el-table-column>
      <el-table-column label="操作" width="250" align="center">
        <template slot-scope="scope">
          <el-button @click="handleUpdate(scope.$index)">修改</el-button>
          <el-button @click="handleDelete(scope.$index)">删除</el-button>
        </template>
      </el-table-column>
    </el-table>

    <pagination v-show="total>0" :total="total" :page.sync="listQuery.pageNum" :limit.sync="listQuery.pageSize" @pagination="getList" />

     <el-dialog
      title="新增用户"
      :visible.sync="dialogVisible"
      width="30%">
      <el-form :model="addForm" :rules="addRules" ref="addForm"  class="demo-addForm" >
        <el-form-item label="用户账号" prop="username">
          <el-input v-model="addForm.username"></el-input>
        </el-form-item>
        <el-form-item label="密码" prop="password">
          <el-input v-model="addForm.password"></el-input>
        </el-form-item>
        <el-form-item label="用户全称" prop="fullName">
          <el-input v-model="addForm.fullname"></el-input>
        </el-form-item>
        <el-form-item label="用户角色" prop="role">
          <el-select v-model="addForm.roleId" placeholder="--请选择--" id="role">
            <el-option v-for="item in roleOptions" :key="item.id"  :label="item.description"  :value="item.id"         
            ></el-option>
          </el-select>
        </el-form-item>
        <el-form-item label="省级" prop="prov">
          <el-select v-model="addForm.pid" placeholder="请选择">
            <el-option v-for="item in provinceOptions" :key="item.id"  :label="item.name"  :value="item.id"         
            ></el-option>
          </el-select>
        </el-form-item>
        <el-form-item label="公司" prop="comp">
          <el-select v-model="addForm.companyId" placeholder="请选择">
            <el-option v-for="item in companyOptions" :key="item.id"  :label="item.name"  :value="item.id"         
            ></el-option>
          </el-select>
        </el-form-item>
      </el-form>
      <span slot="footer" class="dialog-footer">
        <el-button @click="dialogVisible = false">取 消</el-button>
        <el-button type="primary" @click="addUser">确 定</el-button>
      </span>
    </el-dialog>

    <el-dialog
      title="用户编辑"
      :visible.sync="dialogVisible1"
      width="30%">
      <el-form :model="addForm" :rules="addRules" ref="editForm"  class="demo-addForm" >
        <el-form-item label="用户账号" prop="username">
          <el-input v-model="addForm.username"></el-input>
        </el-form-item>
        <el-form-item label="密码" prop="password">
          <el-input v-model="newPassword" type="password"></el-input>
        </el-form-item>
        <el-form-item label="用户全称" prop="fullName">
          <el-input v-model="addForm.fullname"></el-input>
        </el-form-item>
        <el-form-item label="用户角色" prop="role">
          <el-select v-model="addForm.roleId" placeholder="--请选择--" id="role">
            <el-option v-for="item in roleOptions" :key="item.id"  :label="item.description"  :value="item.id"         
            ></el-option>
          </el-select>
        </el-form-item>
        <el-form-item label="省级" prop="prov">
          <el-select v-model="addForm.pid" placeholder="请选择">
            <el-option v-for="item in provinceOptions" :key="item.id"  :label="item.name"  :value="item.id"         
            ></el-option>
          </el-select>
        </el-form-item>
        <el-form-item label="公司" prop="comp">
          <el-select v-model="addForm.companyId" placeholder="请选择">
            <el-option v-for="item in companyOptions" :key="item.id"  :label="item.name"  :value="item.id"         
            ></el-option>
          </el-select>
        </el-form-item>
        <!-- <el-form-item label="工程部" prop="proj">
          <el-select v-model="addForm.project" placeholder="请选择">
            <el-option v-for="item in projectOptions" :key="item.id"  :label="item.name"  :value="item.id"         
            ></el-option>
          </el-select>
        </el-form-item> -->
      </el-form>
      <span slot="footer" class="dialog-footer">
        <el-button @click="dialogVisible1 = false">取 消</el-button>
        <el-button type="primary" @click="update">确 定</el-button>
      </span>
    </el-dialog>

    <el-dialog
      title="删除用户"
      :visible.sync="dialogVisible2"
      width="30%">
      <span>确定要删除这条记录吗？</span>
      <span slot="footer" class="dialog-footer">
        <el-button @click="dialogVisible2 = false">取 消</el-button>
        <el-button type="primary" @click="deleteRow">确 定</el-button>
      </span>
    </el-dialog>
  </div>
</template>

<script>
import moment from 'moment'
import axios from 'axios'
import Pagination from '@/components/Pagination' // Secondary package based on el-pagination
export default {
  name: 'systemUser',
  components: { Pagination },
  data() {
    return {
      roleOptions: [],
      provinceOptions: [],
      companyOptions: [],
      projectOptions: [],
      addForm:{
        id:null,
        username: null,
        password: null,
        fullname: null,
        roleId:'',
        pid:'',
        companyId: ''
        // project:''
      }, 
      newPassword:'woshiceshi',
      // addForm:{
      //   id:'',
      //   username: '',
      //   password: 'woshigeceshi',
      //   fullname: '',
      //   roleId:'',
      //   pid:'',
      //   companyId: ''
      // },
      addRules: {
        username: [
          { required: true,  trigger: 'blur' },
          { min: 3, max: 8, message: '长度在 3 到 8个字符', trigger: 'blur' }
        ],
        password: [
          { required: true,  trigger: 'blur' },
          { min: 6, max: 16, message: '长度在 6 到 16个字符', trigger: 'blur' }
        ]
      },
      dialogVisible:false,
      dialogVisible1:false,
      dialogVisible2:false,
      list: null,
      total: 0,
      listLoading: false,
      listQuery: {
        pageNum: 1,
        pageSize: 10
      },
      id:null

    }
  },
  watch:{
    newPassword(val, oldVal){
      if(val != oldVal){
        this.addForm.password = val
      }
    }
  },
  created() {
    this.getList()
  },
  mounted() {
    this.getRoles()
    this.getProvince()
    this.getCompany()
    this.getProject()
  },
  methods: {
    handleCreate(){
      this.dialogVisible = true
      this.addForm = {
        username: '',
        password: '',
        fullname: '',
        roleId:'',
        pid:'',
        companyId: ''
      }
    },
    addUser() {
      console.log(this.addForm)
      axios.post('http://127.0.0.1:28081/admin/add',{
        body: this.addForm
      }).then(response => {
        this.$message({
        message: '添加成功',
        type: 'success'
        })
        this.dialogVisible = false
        this.getList()
      })
    },
    handleUpdate(index){
      this.dialogVisible1 = true
      this.addForm = this.list[index]
      this.addForm.password = 'woshiceshi'
    },
    update(){
      console.log(this.addForm)
      switch (this.addForm.roleId){
        case '超管':
          this.addForm.roleId = 1
          break
        case '安全员':
          this.addForm.roleId = 2
          break
        case '操作机手':
          this.addForm.roleId = 3
          break
        case '班长-指导员':
          this.addForm.roleId = 4
          break
        case '副班长':
          this.addForm.roleId = 5
          break
        case '技术员-质检员':
          this.addForm.roleId = 6
          break
        case '一般人员':
          this.addForm.roleId = 7
          break
        case '省级':
          this.addForm.roleId = 8
          break
        case '公司':
          this.addForm.roleId = 9
          break
        case '工程部':
          this.addForm.roleId = 10
          break
      }
      switch (this.addForm.pid){
        case '冀北':
          this.addForm.pid = "1"
          break
        default:
          this.addForm.pid = "0"
          break
      }
      switch (this.addForm.companyId){
        case '工程公司':
          this.addForm.companyId = 1
          break
        case '唐山公司':
          this.addForm.companyId = 2
          break
        case '张家口公司':
          this.addForm.companyId = 3
          break
        case '承德公司':
          this.addForm.companyId = 4
          break
        case '秦皇岛公司':
          this.addForm.companyId = 5
          break
        case '廊坊公司':
          this.addForm.companyId = 6
          break
        default:
          this.addForm.companyId = 0
          break
      }
      axios.post('http://127.0.0.1:28081/admin/update',{
        body: this.addForm
      }).then(response => {
        this.$message({
        message: '修改成功',
        type: 'success'
        })
        this.dialogVisible1 = false
        this.getList()
      })
    },
    handleDelete(index){
       this.dialogVisible2 = true
       this.id =  this.list[index].id
    },
    deleteRow(){
      console.log(this.id)
      axios.post('http://127.0.0.1:28081/admin/delete',{
        body:{
          id:this.id
        }
        }).then(response =>{
          console.log(response.data.header.message)
          this.dialogVisible2 = false
          this.getList()
        })
    },
    getList() {
      this.listLoading = true
      axios.post('http://127.0.0.1:28081/admin/queryPageList',{
        header: this.listQuery,
        body: {
        }
      }).then(response => {
        var res = response.data.body.list
        console.log(res)
        res.forEach(element => {
            switch (element.roleId){
              case 1:
                element.roleId = '超管'
                break
              case 2:
                element.roleId = '安全员'
                break
              case 3:
                element.roleId = '操作机手'
                break
              case 4:
                element.roleId = '班长-指导员'
                break
              case 5:
                element.roleId = '副班长'
                break
              case 6:
                element.roleId = '技术员-质检员'
                break
              case 7:
                element.roleId = '一般人员'
                break
              case 8:
                element.roleId = '省级'
                break
              case 9:
                element.roleId = '公司'
                break
              case 10:
                element.roleId = '工程部'
                break
            }
            switch (element.pid){
              case "1":
                element.pid = '冀北'
                break
              default:
                element.pid = '--请选择--'
                break
            }
            switch (element.companyId){
              case 1:
                element.companyId = '工程公司'
                break
              case 2:
                element.companyId = '唐山公司'
                break
              case 3:
                element.companyId = '张家口公司'
                break
              case 4:
                element.companyId = '承德公司'
                break
              case 5:
                element.companyId = '秦皇岛公司'
                break
              case 6:
                element.companyId = '廊坊公司'
                break
              default:
                element.companyId = '--请选择--'
                break
            }
        });
       this.list = res
      
        this.total = response.data.body.total
        // Just to simulate the time of the request
        console.log(this.list)
        setTimeout(() => {
          this.listLoading = false
        }, 1.5 * 1000)
      }).catch(e => console.log(e))

      // fetchList(this.listQuery).then(response => {
      //   this.list = response.data.items
      //   this.total = response.data.total
      //   // Just to simulate the time of the request
      //   setTimeout(() => {
      //     this.listLoading = false
      //   }, 1.5 * 1000)
      // })
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

</style>
