<template>
  <div class="on">
    <div v-if="!showPredisplay">
      <el-row class="row">
        <p class="font">预处理任务表</p>
        <el-button class="button" type="primary" size="small" icon="el-icon-plus"
          @click="MessageBox()">新增预处理任务</el-button>
      </el-row>
      <div class="table1">
        <el-table :data="formLabelAlign" style="width: 100%; height:100%" :header-cell-style="headerRowStyle" border>
          <el-table-column prop="id" label="编号" min-width="80" align="left"></el-table-column>
          <el-table-column prop="name" label="任务名称" min-width="200" align="left"></el-table-column>
          <el-table-column prop="dataset" label="数据集名称" min-width="240" align="left"></el-table-column>
          <el-table-column prop="remark" label="数据集描述" min-width="200" align="left"></el-table-column>
          <el-table-column prop="state" label="状态" min-width="130.5" align="left">
            <template slot-scope="scope">
              <span>
                <!-- 如果状态是“已运行”，显示图片 -->
                <img v-if="scope.row.state == '1'" src="../../../assets/Oval@2x.png" alt="已运行图标"
                  style="width: 14px; height: 14px; margin-right: 3px;">
                <img v-else-if="scope.row.state == '-1'" src="../../../assets/Oval@2x-3.png" alt="未运行图标"
                  style="width: 14px; height: 14px; margin-right: 3px;">
                <img v-else-if="scope.row.state == '0'" src="../../../assets/Oval@2x-2.png" alt="进行中图标"
                  style="width: 14px; height: 14px; margin-right: 3px;">
                <span>
                  {{
                    scope.row.state == '1' ? '已运行' :
                      scope.row.state == '0' ? '进行中' :
                        '未运行'
                  }}
                </span>
              </span>
            </template>
          </el-table-column>
          <el-table-column prop="time" label="创建时间" min-width="200" align="left"></el-table-column>
          <el-table-column fixed="right" label="操作" min-width="150" align="left">
            <template slot-scope="scope">
              <el-button type="text" size="small" align="center" :style="{
                color: scope.row.state == '-1' ? '#1890FF' : '#52C41A'
              }" @click="open(scope.row)">
                {{ scope.row.state == "-1" ? '启动' : '查看' }}
              </el-button>
              <el-button type="text" size="small" :disabled="scope.row.state != '-1'"
                @click="edit(scope.row)">编辑</el-button>
              <el-button type="text" size="small" style="color: #FF4D4F" @click="del(scope.row)">删除</el-button>
            </template>
          </el-table-column>
        </el-table>
        <div class="pagination">
          <el-pagination @size-change="handleSizeChange" @current-change="handleCurrentChange"
            :current-page.sync="currentPage1" :page-size="100" layout="total, prev, pager, next, jumper" :total="1000">
          </el-pagination>
        </div>
      </div>
    </div>

    <div v-if="showPredisplay">
      <router-view></router-view>
    </div>

    <!-- 新增任务的对话框 -->
    <el-dialog title="" :visible.sync="addVisible">

      <div class="dialog-title">
        <span>预处理任务设置</span>
        <i class="el-dialog__headerbtn el-icon-close" @click="addVisible = false"></i>
      </div>

      <el-form class="dialog-content" :rules="rules" ref="formLabelAlign" label-position="right" label-width="112px"
        :model="formLabelAlign" size="small">
        <el-form-item label="任务名称" prop="name">
          <el-input v-model="formLabelAlign.name"></el-input>
        </el-form-item>
        <el-form-item label="数据集描述" prop="remark">
          <el-input v-model="formLabelAlign.remark"></el-input>
        </el-form-item>
        <el-form-item label="创建时间" prop="time">
          <span>{{ formLabelAlign.time }}</span>
        </el-form-item>
        <el-form-item label="选择代码">
          <el-select v-model="formLabelAlign.dataset" placeholder="请选择" style="margin-right: 5px;width:100%;">
            <el-option v-for="item in dbList" :key="item.id" :label="item.dataset" :value="item.dataset"></el-option>
          </el-select>
        </el-form-item>
      </el-form>
      <span slot="footer" class="dialog-footer">
        <el-button @click="addVisible = false">取消</el-button>
        <el-button type="primary" @click="handleSubmit">确认</el-button>
      </span>
    </el-dialog>

    <!-- 编辑任务的对话框 -->
    <el-dialog title="" :visible.sync="editVisible">

      <div class="dialog-title">
        <span>预处理任务设置</span>
        <i class="el-dialog__headerbtn el-icon-close" @click="editVisible = false"></i>
      </div>

      <el-form class="dialog-content" :rules="rules" ref="formLabelAlign" label-position="right" label-width="124px" :model="formLabelAlign"
        size="small">
        <el-form-item label="任务名称" prop="name" style="font-weight: bold;" label-width="124px">
          <el-input v-model="formLabelAlign.name"></el-input>
        </el-form-item>
        <template>
          <el-form-item label="选择已上传代码" style="font-weight: bold;" label-width="124px">
            <el-select v-model="formLabelAlign.dataset" placeholder="请选择">
              <el-option v-for="item in dbList" :key="item.id" :label="item.dataset" :value="item.dataset"></el-option>
            </el-select>
          </el-form-item>
        </template>
        <el-form-item label="创建时间" style="font-weight: bold;" label-width="124px">
          <span>{{ formLabelAlign.time }}</span>
        </el-form-item>
      </el-form>


      <span slot="footer" class="dialog-footer">
        <el-button @click="editVisible = false">取消</el-button>
        <el-button type="primary" @click="handleSubmit">确认</el-button>
      </span>
    </el-dialog>

  </div>
</template>

<script>
import { getTime } from '@/utils/getTime'
import { get, del, post, put } from '../../../api/request';
import { schoolInstance2 } from '../../../axios';

export default {
  data() {
    return {
      formLabelAlign: [],
      showPredisplay: false,  // 控制显示的状态
      addVisible: false, //新增预处理任务对话框显示状态
      radio: '2',  // 默认选中“是”
      selectedOption: null,  // 选中的下拉选项
      editVisible: false,//编辑弹窗显示状态
      formData: [ //本地上传列表

      ],
      //数据集列表
      dbList: [

      ],
      gpuList: [],
      rules: {
        "name": [
          { required: true, message: '请输入名称', trigger: 'blur' },
        ],
        "dataset": [
          { required: true, message: '请选择数据集', trigger: 'change' },
        ],
        "code": [

        ],


      },
    }
  },

  methods: {
    //获取预处理任务列表
    async getPreList() {
      try {
        const response = await get('/preprocessing/getAllPreprocessing');
        if (response.data.total != 0) {
          this.formLabelAlign = response.data.list
        }
        console.log("预处理任务列表", this.formLabelAlign)
      } catch (error) {
        console.log('请求预处理任务列表失败:', error)
      }
    },
    //获取数据集列表
    async getDbList() {
      try {
        const response = await get('/dataset/getAllDatasets');
        if (response.data.total != 0) {
          this.dbList = response.data.list.filter(item => item.result == '未预处理');

        }
        console.log("数据集列表", this.dbList)
      } catch (error) {
        console.log('请求数据集表格数据失败:', error)
      }
    },
    //获取gpu和内存使用率
    async getGpuUsed() {
      try {
        const response = await schoolInstance2.get('/gpu_usage'); // 使用实例的 get 方法
        console.log('gpu和内存使用率:', response); // 直接输出响应数据
        this.gpuList = Object.values(response); // 确保将响应中的数据正确存储到 gpuList        
      } catch (error) {
        console.error('请求错误:', error); // 捕获并输出请求错误
      }
    },
    resetForm() {
      this.formLabelAlign = {
        "id": '',
        "name": '',
        "dataset": '',
        "remark": '',
        "state": '',
        "time": '',
        // "code": '',
        "gpu": ''
      }
    },

    headerRowStyle() {
      return {
        backgroundColor: 'rgba(243, 249, 254, 0.6)',
        color: '#000',
        padding: '17px 16px 17px 0px',
        border: '0.3px solid rgba(240, 244, 248, 0.6)',
        marginTop: '0px'
      }
    },
    open(row) {
      this.showPredisplay = true;
      this.$router.push({ path: `/home/predisplay`, query: { row: JSON.stringify(row) } });
      console.log("row", row)
    },
    MessageBox() {
      this.resetForm()
      this.formLabelAlign.time = getTime()
      this.addVisible = true;
    },
    edit(row) {
      this.resetForm()
      this.formLabelAlign = {
        "id": row.id,
        "name": row.name || '默认值',
        "dataset": row.dataset || '默认值',
        "time": row.time || '',
        "gpu": row.gpu || '',
        "selected": row.selected || '',
        "fileList": row.fileList || ''
      }
      console.log("row", row)
      this.editVisible = true;
    },
    handleSubmit() {
      console.log("formLabelAlign", this.formLabelAlign)
      this.$refs.formLabelAlign.validate((valid) => {
        if (valid) {
          //状态设为未运行
          this.formLabelAlign.state = -1;
          post(`/preprocessing/addPreprocessing`, this.formLabelAlign)
            .then(response => {
              alert('提交成功');
              this.addVisible = false;  // 关闭对话框
              this.getPreList();
            })
            .catch(error => {
              console.error('提交失败:', error);
            });
        }
      });
    },
    handleRemove(item) {
      console.log('Removing item:', item);
    },
    beforeRemove(item) {
      // 处理删除前的逻辑
      console.log('Preparing to remove item:', item);
    },
    handlePreview() {
      // 这里放置预览逻辑
      console.log("图片预览触发");
    },
    del(row) {
      console.log(row)
      this.$confirm('是否删除当前数据?', '提示', {
        confirmButtonText: '确定',
        cancelButtonText: '取消',
        type: 'warning'
      }).then(async () => {
        const response = await del(`/preprocessing/deletePreprocessing/${row.id}`)
        console.log("res", response)
        //删除tableData中数据
        if (response.errmsg == "成功") {
          this.formLabelAlign = this.formLabelAlign.filter(item => item.id !== row.id)
          this.$message({
            type: 'success',
            message: '删除成功!'
          });
        } else {
          this.$message.error('删除失败，请稍后重试')
        }
      }).catch(() => {
        this.$message({
          type: 'info',
          message: '已取消删除'
        });
      });
    }
  },
  mounted() {
    this.getDbList();
    this.getPreList();
  },

}
</script>

<style scoped>
@import "../../../css/preTask.css";
</style>