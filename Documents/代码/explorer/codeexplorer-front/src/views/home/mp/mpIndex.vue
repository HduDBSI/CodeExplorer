<template>
  <div class="on1">
    <div class="table2">
      <el-table :data="tableData" style="width: 100%; height:100%" :header-cell-style="headerRowStyle" border>
        <el-table-column prop="id" label="编号" min-width="80" align="left"></el-table-column>
        <el-table-column prop="name" label="名称" min-width="140" align="left"></el-table-column>
        <el-table-column prop="model" label="模型" min-width="200" align="left">
          <template slot-scope="scope">
            <p>{{ scope.row.model }}</p>

          </template>
        </el-table-column>
        <el-table-column prop="type" label="模型类型" min-width="125" align="left">
          <template slot-scope="scope">

            <el-tag size="small">{{ scope.row.type }}</el-tag>
          </template>
        </el-table-column>
        <el-table-column prop="summary" label="描述" min-width="282" align="left"></el-table-column>
        <el-table-column prop="createTime" label="创建日期" min-width="180" align="left"></el-table-column>
        <el-table-column fixed="right" label="操作" min-width="100" align="left">
          <template v-slot="scope">
            <el-button type="text" size="small" @click="open(scope.row)">查看</el-button>
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
    <!-- 查看模型的训练信息对话框 -->
    <el-dialog title="" :visible.sync="look" width="586px">
      <div class="dialog-title">
        <span>模型训练参数</span>
        <i class="el-dialog__headerbtn el-icon-close" @click="look = false"></i>
      </div>
      <el-form class="dialog-content" ref="formLabelAlign" label-position="right" label-width="112px"
        :model="formLabelAlign" size="small">
        <el-form-item label="任务名称" style="font-weight: bold;" prop="name">
          <p style="font-weight: normal;font-size: 14px;">{{ formLabelAlign.name }}</p>
        </el-form-item>
        <el-form-item label="输出模型名称" style="font-weight: bold; white-space: nowrap;" prop="model">
          <p style="font-weight: normal;font-size: 14px;">{{ formLabelAlign.model }}</p>
        </el-form-item>
        <el-form-item label="任务类型" style="font-weight: bold;" label-width="103px" prop="type">
          <p style="font-weight: normal;font-size: 14px;">{{ formLabelAlign.type }}</p>
        </el-form-item>
        <el-form-item label="数据集" style="font-weight: bold;" prop="datasetId">
          <p style="font-weight: normal;font-size: 14px;">{{ formLabelAlign.dataset }}</p>
        </el-form-item>
        <el-form-item label="模型描述" style="font-weight: bold;" prop="remark">
          <p style="font-weight: normal;font-size: 14px;">{{ formLabelAlign.remark }}</p>
        </el-form-item>

        <!-- 参数 -->
        <el-form-item label="参数设置">
          <el-form class="dialog-form-content" ref="formLabelAlign" label-position="right"
            label-width="200px" :model="formLabelAlign" size="small">
            <el-form-item label="epochs" prop="epochs" style="padding-top: 16px;">
              <p style="width:167px;">{{ formLabelAlign.epochs }}</p>
            </el-form-item>
            <el-form-item label="batch_size" prop="batch_size">
              <p style="width:167px;">{{ formLabelAlign.batch_size }}</p>
            </el-form-item>
            <el-form-item label="学习率" prop="lr">
              <p style="width:167px;">{{ formLabelAlign.lr }}</p>
            </el-form-item>
            <el-form-item label="学习衰减因子" prop="lr_decay_factor">
              <p style="width:167px;">{{ formLabelAlign.lr_decay_factor }}</p>
            </el-form-item>
            <el-form-item label="分类数" prop="num_classes">
              <p style="width:167px;">{{ formLabelAlign.num_classes }}</p>
            </el-form-item>
            <el-form-item label="学习率衰减步长" prop="lr_decay_step_size">
              <p style="width:167px;">{{ formLabelAlign.lr_decay_step_size }}</p>
            </el-form-item>
            <el-form-item label="权重衰减系数" prop="weight_decay">
              <p style="width:167px;">{{ formLabelAlign.weight_decay }}</p>
            </el-form-item>
            <el-form-item label="隐藏层神经元数量" prop="hidden">
              <p style="width:167px;">{{ formLabelAlign.hidden }}</p>
            </el-form-item>
            <el-form-item label="Dropout概率" prop="dropout">
              <p style="width:167px;">{{ formLabelAlign.dropout }}</p>
            </el-form-item>
            <el-form-item label="多头注意力机制头数" prop="nheads">
              <p style="width:167px;">{{ formLabelAlign.nheads }}</p>
            </el-form-item>
            <el-form-item label="LeakyReLU的负斜率系数" prop="alpha" style="padding-bottom: 16px;">
              <p style="width:167px;">{{ formLabelAlign.alpha }}</p>
            </el-form-item>
          </el-form>
        </el-form-item>
      </el-form>
      <div class="dialog-footer">
        <span>
        <el-button @click="look = false">关闭</el-button>

      </span>
      </div>
     

    </el-dialog>
  </div>
</template>

<script>
// import companyInstance from '../../../axios';
import { get, del, post } from '../../../api/request';
export default {
  data() {
    return {
      tableData: [
        // { id: 1, name: '孪生图匹配网络1', model: 'CodeGraph4CCDetector', tabel: '代码克隆', summary: '一种基于 CFG/PDG 注意力机制的孪生图匹配网络，它可以在 token、语句和图级别捕获关键信息，以充分学习代码语义', createTime: '2024.08.14' },
        // { id: 2, name: '孪生图匹配网络2', model: 'CodeGraph4CCDetector', tabel: '代码克隆', summary: '一种基于 CFG/PDG 注意力机制的孪生图匹配网络，它可以在 token、语句和图级别捕获关键信息，以充分学习代码语义', time: '2024.08.14' },

      ],
      look: false,
      taskId: '',
      formLabelAlign: {         // 新增表单
        "id": '',
        "name": '任务名称',
        "datasetId": '',
        "model": '',
        "dataset": '',
        "summary": '',
        "state": '',
        "type": '',
        "gpu": '',
        "epochs": '',
        "batch_size": '',
        "lr": '',
        "lr_decay_factor": '',
        "weight_decay": '',
        "lr_decay_step_size": '',
        "num_classes": '',
        "hidden": '',
        "dropout": '',
        "nheads": '',
        "alpha": '',
        "time": '',
        "result": '',
        "remark": '',
      },
    }
  },
  methods: {
    resetForm() {
      this.formLabelAlign = {
        "id": '',
        "name": '',
        "datasetId": '',
        "model": '',
        "dataset": '',
        "summary": '',
        "state": '',
        "type": '',
        "gpu": '',
        "epochs": '',
        "batch_size": '',
        "lr": '',
        "lr_decay_factor": '',
        "weight_decay": '',
        "lr_decay_step_size": '',
        "num_classes": '',
        "hidden": '',
        "dropout": '',
        "nheads": '',
        "alpha": '',
        "time": '',
        "result": '',
        "remark": '',
        // epoch: '',
        // batch_size: '',
        // lr: '',
        // lr_decay_factor: '',
        // weight_decay: '',
        // lr_decay_step_size: '',
        // num_classes: '',
        // hidden: '',
        // dropout: '',
        // nheads: '',
        // alpha: '',
        // time: ''
      }
    },
    headerRowStyle() {
      return {
        backgroundColor: 'rgba(243, 249, 254, 0.6)',
        color: '#000',
        padding: '17px 16px 17px 0px',
        border: '0.3px solid rgba(240, 244, 248, 0.6)',
        // marginTop: '24px'
      }
    },
    //获取模型列表
    // async getModelList() {
    //   try {
    //     const response = await get('/modelList/getAllModels');
    //     const trainResponse = await get('/train/getAllTrain', { params: { remark: item.remark } });
    //     this.tableData = response.data.list
    //   } catch (error) {
    //     console.log('请求模型表格数据失败:', error)
    //   }
    // },
    async getModelList() {
      try {
        const response = await get('/modelList/getAllModels');
        const updatedTableData = await Promise.all(response.data.list.map(async (item) => {
          try {
            const trainResponse = await get(`/train/getTrainById/${item.remark}`);
            if (trainResponse) {
              item.type = trainResponse.data.type; // 合并训练数据
            } else {
              console.log(`未找到训练数据，remark: ${item.remark}`);
              item.trainData = null; // 如果没有找到匹配的训练数据，可以设置为 null
            }

            return item; 
          } catch (trainError) {
            console.log(`请求 train 数据失败, remark: ${item.remark}`, trainError);
            item.type = null; 
            return item; 
          }
        }));
        console.log('updatedTableData',updatedTableData)
        // 更新 tableData，包含了 train 数据
        this.tableData = updatedTableData;
      } catch (error) {
        console.log('请求模型表格数据失败:', error);
      }
    },
    //根据模型id查询对应的任务id
    async getModelListId(id) {

      const response = get(`/modelList/getModelById/${id}`)
        .then((response) => {
          get(`/train/getTrainById/${response.data.remark}`)
            .then((res) => {
              this.formLabelAlign = res.data
            })
        })

    },

    del(row) {
      this.$confirm('是否删除当前数据?', '提示', {
        confirmButtonText: '确定',
        cancelButtonText: '取消',
        type: 'warning'
      }).then(async () => {
        const response = await del(`/modelList/deleteModelById/${row.id}`)
        this.tableData = this.tableData.filter(item => item.id !== row.id)
        this.$message({
          type: 'success',
          message: '删除成功!'
        });

      }).catch(() => {
        this.$message({
          type: 'info',
          message: '已取消删除'
        });
      });
    },
    open(row) {
      this.look = true
      this.taskId = row.id
      this.getModelListId(row.id)
    }
  },
  mounted() {
    this.getModelList();
  }
}
</script>

<style scoped>
@import"../../../css/mpIndex.css";
</style>