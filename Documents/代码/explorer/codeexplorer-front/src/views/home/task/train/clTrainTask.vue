<template>
    <div class="on">
        <!-- 训练任务 -->
        <div v-if="!showTrainDisplay">
            <el-row class="row">
                <p class="font">代码克隆检测任务表</p>
                <el-button class="button" type="primary" size="small" icon="el-icon-plus" @click="MessageBox">
                    新增训练任务
                </el-button>
            </el-row>
            <div class="table1">
                <el-table :data="tableData" style="width: 100%; height:100%" :header-cell-style="headerRowStyle" border>
                    <el-table-column prop="id" label="编号" min-width="80" align="left"></el-table-column>
                    <el-table-column prop="name" label="任务名称" min-width="200" align="left">

                    </el-table-column>
                    <el-table-column prop="type" label="任务类型" min-width="150" align="left">
                        <template slot-scope="scope">
                            <el-tag size="small" class="tab">{{ scope.row.type }}</el-tag>
                        </template>
                    </el-table-column>
                    <el-table-column prop="model" label="输出模型名称" min-width="180" align="left"></el-table-column>
                    <el-table-column prop="dataset" label="数据集名称" min-width="240" align="left"></el-table-column>
                    <el-table-column prop="state" label="状态" min-width="130.5" align="left">
                        <template slot-scope="scope">
                            <span>
                                <img v-if="scope.row.state == '1'" src="../../../../assets/Oval@2x.png" alt="已运行图标"
                                    style="width: 14px; height: 14px; margin-right: 3px;">
                                <img v-else-if="scope.row.state == '-1'" src="../../../../assets/Oval@2x-3.png"
                                    alt="未运行图标" style="width: 14px; height: 14px; margin-right: 3px;">
                                <img v-else-if="scope.row.state == '0'" src="../../../../assets/Oval@2x-2.png"
                                    alt="进行中图标" style="width: 14px; height: 14px; margin-right: 3px;">
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
                    <el-table-column prop="remark" label="模型描述" min-width="200" align="left"></el-table-column>
                    <el-table-column prop="time" label="创建时间" min-width="200" align="left"></el-table-column>
                    <el-table-column fixed="right" label="操作" min-width="150" align="left">
                        <template slot-scope="scope">
                            <el-button type="text" size="small" align="center" :style="{
                                color: scope.row.state == '-1' ? '#1890FF' : '#52C41A'
                            }" @click="open(scope.row)">
                                {{ scope.row.state == "-1" ? '启动' : '查看' }}
                            </el-button>
                            <el-button type="text" size="small" align="center" :disabled="scope.row.state != '-1'"
                                @click="edit(scope.row)">编辑</el-button>
                            <el-button type="text" size="small" align="center" style="color: #FF4D4F"
                                @click="del(scope.row)">删除</el-button>
                        </template>
                    </el-table-column>
                </el-table>
                <div class="pagination">
                    <el-pagination @size-change="handleSizeChange" @current-change="handleCurrentChange"
                        :current-page.sync="currentPage1" :page-size="100" layout="total, prev, pager, next, jumper"
                        :total="1000">
                    </el-pagination>
                </div>
            </div>
        </div>

        <div v-if="showTrainDisplay">
            <router-view></router-view>
        </div>

        <!-- 新增训练任务的对话框 -->
        <el-dialog title="" :visible.sync="addVisible">
            <div class="dialog-title">
                <span>训练任务设置</span>
                <i class="el-dialog__headerbtn el-icon-close" @click="addVisible = false"></i>
            </div>
            <el-form class="dialog-content" :rules="rules" ref="formLabelAlign" label-position="right"
                label-width="112px" :model="formLabelAlign" size="small">
                <el-form-item label="任务名称" prop="name">
                    <el-input v-model="formLabelAlign.name" placeholder="请输入任务名称"></el-input>
                </el-form-item>
                <el-form-item label="输出模型名称" prop="model">
                    <el-input v-model="formLabelAlign.model" placeholder="请输入输出模型名称"></el-input>
                </el-form-item>
                <el-form-item label="模型描述" prop="remark">
                    <el-input v-model="formLabelAlign.remark" placeholder="请输入模型描述"></el-input>
                </el-form-item>

                <el-form-item label="任务类型" prop="type">
                    <div style="display: flex; align-items: center;">
                        <el-select v-model="formLabelAlign.type" placeholder="请选择" style="width:100%;">
                            <el-option label="代码克隆检测" value="代码克隆检测"></el-option>
                            <el-option label="代码异味检测" value="代码异味检测"></el-option>
                            <el-option label="特征嫉妒检测" value="特征嫉妒检测"></el-option>
                        </el-select>
                    </div>
                </el-form-item>

                <el-form-item label="数据集" prop="dataset_id">
                    <el-select v-model="formLabelAlign.dataset_id" placeholder="请选择数据集"
                        style="margin-right: 5px;width:100%;">
                        <el-option v-for="item in dbList" :key="item.id" :label="item.dataset" :value="item.id">
                        </el-option>
                    </el-select>
                </el-form-item>

                <!-- 参数设置 -->
                <el-form-item label="参数设置">
                    <el-form class="dialog-form-content" :rules="rules" ref="formLabelAlign" label-position="right"
                        label-width="200px" :model="formLabelAlign" size="small">
                        <el-form-item label="epochs" prop="epochs" style="padding-top: 16px;">
                            <el-input v-model="formLabelAlign.epochs" style="width:167px;"></el-input>
                        </el-form-item>
                        <el-form-item label="batch_size" prop="batch_size">
                            <el-input v-model="formLabelAlign.batch_size" style="width:167px;"></el-input>
                        </el-form-item>
                        <el-form-item label="学习率" prop="lr">
                            <el-input v-model="formLabelAlign.lr" style="width:167px;"></el-input>
                        </el-form-item>
                        <el-form-item label="学习衰减因子" prop="lr_decay_factor">
                            <el-input v-model="formLabelAlign.lr_decay_factor" style="width:167px;"></el-input>
                        </el-form-item>
                        <el-form-item label="分类数" prop="num_classes">
                            <el-input v-model="formLabelAlign.num_classes" style="width:167px;"></el-input>
                        </el-form-item>
                        <el-form-item label="学习率衰减步长" prop="lr_decay_step_size">
                            <el-input v-model="formLabelAlign.lr_decay_step_size" style="width:167px;"></el-input>
                        </el-form-item>
                        <el-form-item label="权重衰减系数" prop="weight_decay">
                            <el-input v-model="formLabelAlign.weight_decay" style="width:167px;"></el-input>
                        </el-form-item>
                        <el-form-item label="隐藏层神经元数量" prop="hidden">
                            <el-input v-model="formLabelAlign.hidden" style="width:167px;"></el-input>
                        </el-form-item>
                        <el-form-item label="Dropout概率" prop="dropout">
                            <el-input v-model="formLabelAlign.dropout" style="width:167px;"></el-input>
                        </el-form-item>
                        <el-form-item label="多头注意力机制头数" prop="nheads">
                            <el-input v-model="formLabelAlign.nheads" style="width:167px;"></el-input>
                        </el-form-item>
                        <el-form-item label="LeakyReLU的负斜率系数" prop="alpha" style="padding-bottom: 16px;">
                            <el-input v-model="formLabelAlign.alpha" style="width:167px;"></el-input>
                        </el-form-item>
                    </el-form>
                </el-form-item>
                <!-- <el-row>
                    <el-col :span="11">
                        <el-form-item label="epochs" label-width="108px" prop="epochs">
                            <el-input v-model="formLabelAlign.epochs" placeholder="epochs"></el-input>
                        </el-form-item>
                    </el-col>
                    <el-col :span="13">
                        <el-form-item label="batch_size" label-width="150px" prop="batch_size">
                            <el-input v-model="formLabelAlign.batch_size" placeholder="batch_size"></el-input>
                        </el-form-item>
                    </el-col>
                </el-row>

                <el-row>
                    <el-col :span="11">
                        <el-form-item label="学习率" label-width="108px" prop="lr">
                            <el-input v-model="formLabelAlign.lr" placeholder="lr"></el-input>
                        </el-form-item>
                    </el-col>
                    <el-col :span="13">
                        <el-form-item label="学习衰减因子" label-width="150px" style="white-space: nowrap;"
                            prop="lr_decay_factor">
                            <el-input v-model="formLabelAlign.lr_decay_factor" placeholder="lr_decay_factor"></el-input>
                        </el-form-item>
                    </el-col>
                </el-row>

                <el-row>
                    <el-col :span="11">
                        <el-form-item label="分类数" label-width="108px" prop="num_classes">
                            <el-input v-model="formLabelAlign.num_classes" placeholder="num_classes"></el-input>
                        </el-form-item>
                    </el-col>
                    <el-col :span="13">
                        <el-form-item label="学习率衰减步长" label-width="150px" prop="lr_decay_step_size">
                            <el-input v-model="formLabelAlign.lr_decay_step_size" style="width: 100%;"
                                placeholder="lr_decay_step_size"></el-input>
                        </el-form-item>
                    </el-col>
                </el-row>

                <el-row>
                    <el-col :span="11">
                        <el-form-item label="权重衰减系数" label-width="108px" prop="weight_decay">
                            <el-input v-model="formLabelAlign.weight_decay" placeholder="weight_decay"></el-input>
                        </el-form-item>
                    </el-col>
                    <el-col :span="13">
                        <el-form-item label="隐藏层神经元数量" label-width="150px" prop="hidden">
                            <el-input v-model="formLabelAlign.hidden" placeholder="hidden"></el-input>
                        </el-form-item>
                    </el-col>
                </el-row>

                <el-row>
                    <el-col :span="11">
                        <el-form-item label="Dropout概率" label-width="108px" style="white-space: nowrap;" prop="dropout">
                            <el-input v-model="formLabelAlign.dropout" placeholder="dropout"></el-input>
                        </el-form-item>
                    </el-col>
                    <el-col :span="13">
                        <el-form-item label="多头注意力机制头数" label-width="150px" prop="nheads">
                            <el-input v-model="formLabelAlign.nheads" placeholder="nheads"></el-input>
                        </el-form-item>
                    </el-col>
                </el-row>

                <el-row>
                    <el-col :span="11">
                        <el-form-item label="LeakyReLU的负斜率系数" label-width="108px" prop="alpha">
                            <el-input v-model="formLabelAlign.alpha" placeholder="alpha"></el-input>
                        </el-form-item>
                    </el-col>
                </el-row> -->

                <!-- 创建时间 -->
                <el-form-item label="创建时间" style="font-weight: bold;">
                    <span>{{ formLabelAlign.time }}</span>
                </el-form-item>
            </el-form>
            <span slot="footer" class="dialog-footer">
                <el-button @click="addVisible = false">取消</el-button>
                <el-button type="primary" @click="handleSubmit">确认</el-button>
            </span>
        </el-dialog>

        <!-- 修改训练任务的对话框 -->
        <el-dialog title="" :visible.sync="updateVisible">
            <div class="dialog-title">
                <span>训练任务设置</span>
                <i class="el-dialog__headerbtn el-icon-close" @click="updateVisible = false"></i>
            </div>
            <el-form class="dialog-content" :rules="rules" ref="formLabelAlign" label-position="right"
                label-width="112px" :model="formLabelAlign" size="small">
                <el-form-item label="任务名称" prop="name">
                    <el-input v-model="formLabelAlign.name"></el-input>
                </el-form-item>
                <el-form-item label="输出模型名称" prop="model">
                    <el-input v-model="formLabelAlign.model"></el-input>
                </el-form-item>
                <el-form-item label="任务类型" prop="type">
                    <div style="display: flex; align-items: center;">
                        <el-select v-model="formLabelAlign.type" placeholder="请选择" style="width: 100%;">
                            <el-option label="代码克隆检测" value="代码克隆检测"></el-option>
                            <el-option label="代码异味检测" value="代码异味检测"></el-option>
                            <el-option label="特征嫉妒检测" value="特征嫉妒检测"></el-option>
                        </el-select>
                    </div>
                </el-form-item>
                <el-form-item label="数据集" prop="dataset_id" style="width: 100%;">
                    <el-select v-model="formLabelAlign.dataset_id" placeholder="请选择数据集">
                        <el-option v-for="item in dbList" :key="item.id" :label="item.dataset" :value="item.id">
                        </el-option>
                    </el-select>
                </el-form-item>

                <el-form-item label="模型描述" prop="remark">
                    <el-input v-model="formLabelAlign.remark"></el-input>
                </el-form-item>

                <!-- 参数设置 -->
                <el-form-item label="参数设置">
                    <el-form class="dialog-form-content" :rules="rules" ref="formLabelAlign" label-position="right"
                        label-width="200px" :model="formLabelAlign" size="small">
                        <el-form-item label="epochs" prop="epochs" style="padding-top: 16px;">
                            <el-input v-model="formLabelAlign.epochs" style="width:167px;"></el-input>
                        </el-form-item>
                        <el-form-item label="batch_size" prop="batch_size">
                            <el-input v-model="formLabelAlign.batch_size" style="width:167px;"></el-input>
                        </el-form-item>
                        <el-form-item label="学习率" prop="lr">
                            <el-input v-model="formLabelAlign.lr" style="width:167px;"></el-input>
                        </el-form-item>
                        <el-form-item label="学习衰减因子" prop="lr_decay_factor">
                            <el-input v-model="formLabelAlign.lr_decay_factor" style="width:167px;"></el-input>
                        </el-form-item>
                        <el-form-item label="分类数" prop="num_classes">
                            <el-input v-model="formLabelAlign.num_classes" style="width:167px;"></el-input>
                        </el-form-item>
                        <el-form-item label="学习率衰减步长" prop="lr_decay_step_size">
                            <el-input v-model="formLabelAlign.lr_decay_step_size" style="width:167px;"></el-input>
                        </el-form-item>
                        <el-form-item label="权重衰减系数" prop="weight_decay">
                            <el-input v-model="formLabelAlign.weight_decay" style="width:167px;"></el-input>
                        </el-form-item>
                        <el-form-item label="隐藏层神经元数量" prop="hidden">
                            <el-input v-model="formLabelAlign.hidden" style="width:167px;"></el-input>
                        </el-form-item>
                        <el-form-item label="Dropout概率" prop="dropout">
                            <el-input v-model="formLabelAlign.dropout" style="width:167px;"></el-input>
                        </el-form-item>
                        <el-form-item label="多头注意力机制头数" prop="nheads">
                            <el-input v-model="formLabelAlign.nheads" style="width:167px;"></el-input>
                        </el-form-item>
                        <el-form-item label="LeakyReLU的负斜率系数" prop="alpha" style="padding-bottom: 16px;">
                            <el-input v-model="formLabelAlign.alpha" style="width:167px;"></el-input>
                        </el-form-item>
                    </el-form>
                </el-form-item>

                <!-- 创建时间 -->
                <el-form-item label="创建时间" style="font-weight: bold;">
                    <span>{{ formLabelAlign.time }}</span>
                </el-form-item>
            </el-form>
            <span slot="footer" class="dialog-footer">
                <el-button @click="updateVisible = false">取消</el-button>
                <el-button type="primary" @click="updateSubmit">确认</el-button>
            </span>
        </el-dialog>

    </div>
</template>

<script>
import { getTime } from '@/utils/getTime'
import { get, del, post, put } from '../../../../api/request';
import { schoolInstance2, companyInstance } from '../../../../axios';

export default {

    data() {
        return {
            tableData: [

            ],

            showTrainDisplay: false,  // 控制显示的状态
            addVisible: false, // 新增训练任务对话框显示状态
            updateVisible: false, // 修改训练任务对话框显示状态
            formLabelAlign: {         // 新增表单
                "id": '',
                "name": '',
                "dataset_id": '',
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
            rules: {
                name: [{ required: true, message: '请输入', trigger: 'blur' }],
                model: [{ required: true, message: '请输入', trigger: 'blur' }],
                dataset_id: [{ required: true, message: '请输入', trigger: 'blur' }],
                epochs: [{ required: true, message: '请输入', trigger: 'blur' }],
                batch_size: [{ required: true, message: '请输入', trigger: 'blur' }],
                lr: [{ required: true, message: '请输入', trigger: 'blur' }],
                lr_decay_factor: [{ required: true, message: '请输入', trigger: 'blur' }],
                weight_decay: [{ required: true, message: '请输入', trigger: 'blur' }],
                lr_decay_step_size: [{ required: true, message: '请输入', trigger: 'blur' }],
                num_classes: [{ required: true, message: '请输入', trigger: 'blur' }],
                hidden: [{ required: true, message: '请输入', trigger: 'blur' }],
                dropout: [{ required: true, message: '请输入', trigger: 'blur' }],
                nheads: [{ required: true, message: '请输入', trigger: 'blur' }],
                alpha: [{ required: true, message: '请输入', trigger: 'blur' }],
            },
            //数据集表单
            dbList: [
                // {dataset:"默认数据集",id:6,state:1,time:"2011-11-23 13:02:47"}
            ]
        }
    },
    methods: {
        //重制表单
        resetForm() {
            this.formLabelAlign = {
                "id": '',
                "name": '',
                "dataset_id": '',
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
        //获取训练任务列表
        async getTrainList() {
            try {
                const response = await get('/train/getAllTrain');
                if (response.data.total != 0) {
                    this.tableData = response.data.list
                }
                console.log("训练任务列表", this.tableData)
            } catch (error) {
                console.log('请求训练任务数据失败:', error)
            }
        },

        //获取数据集列表
        async getDbList() {
            try {
                const response = await get('/dataset/getAllDatasets');
                if (response.data.total != 0) {
                    this.dbList = response.data.list.filter(item => item.state == 1);
                }
                console.log("数据集列表", this.dbList)
            } catch (error) {
                console.log('请求数据集表格数据失败:', error)
            }
        },
        //表头行样式
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
            console.log("row", row)
            this.$router.push({ path: `/home/cltraindisplay`, query: { row: JSON.stringify(row) } });  // 导航到子路由
            this.showTrainDisplay = true;  // 设置状态以显示子路由内容
        },
        edit(row) {
            this.resetForm()
            this.updateVisible = true;
            console.log(row)
            this.formLabelAlign = {
                "id": row.id || '默认值',
                "name": row.name || '默认值',
                "dataset_id": row.dataset_id || '默认值',
                "model": row.model || '默认值',
                "dataset": row.dataset || '默认值',
                "summary": row.summary || '默认值',
                "state": row.state || '默认值',
                "type": row.type || '代码克隆',
                "gpu": '',
                "epochs": row.epochs || '100',
                "batch_size": row.batch_size || '64',
                "lr": row.lr || '0.0001',
                "lr_decay_factor": row.lr_decay_factor || '0.5',
                "weight_decay": row.weight_decay || '0.0005',
                "lr_decay_step_size": row.lr_decay_step_size || '50',
                "num_classes": row.num_classes || '2',
                "hidden": row.hidden || '16',
                "dropout": row.dropout || '0.1',
                "nheads": row.nheads || '16',
                "alpha": row.alpha || '0.2',
                "remark": row.remark || '',
                "time": row.time || '',
            };


        },
        MessageBox() {
            this.resetForm()
            this.formLabelAlign = {
                "id": '',
                "name": '',
                "dataset_id": '',
                "model": '',
                "dataset": '',
                "summary": '',
                "state": '',
                "type": '',
                "gpu": '',
                "epochs": '100',
                "batch_size": '64',
                "lr": '0.0001',
                "lr_decay_factor": '0.5',
                "weight_decay": '0.0005',
                "lr_decay_step_size": '50',
                "num_classes": '2',
                "hidden": '16',
                "dropout": '0.1',
                "nheads": '16',
                "alpha": '0.2',
                "time": '',
                "result": '',
                "remark": '',

            };
            this.formLabelAlign.time = getTime()
            this.addVisible = true;
        },
        handleSubmit() {
            this.$refs.formLabelAlign.validate((valid) => {
                if (valid) {
                    //通过dataset_id找到dataset的名字
                    this.formLabelAlign.dataset = this.dbList.find(item => item.id == this.formLabelAlign.dataset_id).dataset;
                    //状态设为未运行
                    this.formLabelAlign.state = -1;

                    console.log("formLabelAlign", this.formLabelAlign)
                    const result1 = post('/train/addTrain', this.formLabelAlign, companyInstance)
                        .then(response => {
                            console.log('响应数据:', response);
                            this.addVisible = false;  // 关闭对话框
                            this.getTrainList();
                        })
                        .catch(error => {
                            console.error('请求错误:', error);
                        });
                }
            });
        },
        updateSubmit() {
            this.$refs.formLabelAlign.validate((valid) => {
                if (valid) {
                    //状态设为未运行
                    console.log("修改列表", this.formLabelAlign)
                    this.formLabelAlign.dataset = this.dbList.find(item => item.id == this.formLabelAlign.dataset_id).dataset;
                    put(`/train/updateTrain`, this.formLabelAlign)
                        .then(response => {
                            this.updateVisible = false;  // 关闭对话框
                            this.getTrainList();
                        })
                        .catch(error => {
                            console.error('提交失败:', error);
                        });
                }
            });
        },
        handleSizeChange() { },
        handleCurrentChange() { },
        del(row) {
            console.log(row)
            this.$confirm('是否删除当前数据?', '提示', {
                confirmButtonText: '确定',
                cancelButtonText: '取消',
                type: 'warning'
            }).then(async () => {
                //点击确认
                try {
                    //请求接口
                    const response = await del(`/train/deleteTrain/${row.id}`)
                    console.log("res", response)
                    //删除tableData中数据
                    if (response.errmsg === "成功") {
                        this.tableData = this.tableData.filter(item => item.id !== row.id)

                        //提示删除成功
                        this.$message({
                            type: 'success',
                            message: '删除成功!'
                        });
                    } else {
                        this.$message.error('删除失败，请稍后重试')
                    }
                } catch (error) {
                    console.error('删除时出错:', error);
                    this.$message.error('请求失败，请稍后再试')
                }
            }).catch(() => {
                this.$message({
                    type: 'info',
                    message: '已取消删除'
                });
            });
        },

    },
    mounted() {
        this.getTrainList();
        this.getDbList();
    }
}
</script>

<style scoped>
@import"../../../../css/trainTask.css";
</style>
