<template>
    <div class="on">
        <div v-if="!showTestDisplay">

            <el-row class="row">
                <p class="font">代码克隆测试任务表</p>
                <el-button class="button" type="primary" size="small" icon="el-icon-plus" @click="add">
                    新增测试任务
                </el-button>
            </el-row>

            <div class="table1">
                <el-table :data="tableData" style="width: 100%; height:100%" :header-cell-style="headerRowStyle" border>
                    <el-table-column prop="id" label="编号" min-width="80" align="left"></el-table-column>
                    <el-table-column prop="name" label="任务名称" min-width="200" align="left"></el-table-column>
                    <el-table-column prop="model" label="模型" min-width="200" align="left"></el-table-column>
                    <el-table-column prop="dataset" label="数据集" min-width="200" align="left"></el-table-column>
                    <el-table-column prop="state" label="状态" min-width="130.5" align="left">
                        <template slot-scope="scope">
                            <span>
                                <!-- 如果状态是“已运行”，显示图片 -->
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
                    <el-table-column prop="remark" label="描述" min-width="300" align="left"></el-table-column>
                    <el-table-column prop="time" label="创建时间" min-width="200" align="left"></el-table-column>
                    <el-table-column fixed="right" label="操作" min-width="150" align="left">
                        <template slot-scope="scope">
                            <!-- 如果 state 为 '已运行'，按钮文本为 '查看' -->
                            <el-button type="text" size="small" align="center" :style="{
                                color: scope.row.state == '-1' ? '#1890FF' : '#52C41A'
                            }" @click="open(scope.row)">
                                {{ scope.row.state == "-1" ? '启动' : '查看' }}
                            </el-button>
                            <el-button type="text" size="small" align="center" :disabled="scope.row.state != '-1'"
                                @click="edit(scope.row)">编辑</el-button>
                            <el-button type="text" size="small" align="center" @click="del(scope.row)"
                                style="color: #FF4D4F">删除</el-button>
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

        <div v-if="showTestDisplay">
            <!-- 子路由显示的内容 -->
            <router-view></router-view>
        </div>

        <!-- 新增测试任务的对话框 -->
        <el-dialog title="" :visible.sync="addVisible">

            <div class="dialog-title">
                <span>测试任务设置</span>
                <i class="el-dialog__headerbtn el-icon-close" @click="addVisible = false"></i>
            </div>

            <el-form class="dialog-content" :rules="rules" ref="formLabelAlign" label-position="right"
                label-width="100px" :model="formLabelAlign" size="small">
                <!-- 任务名称 -->
                <el-form-item label="任务名称" prop="name" style="font-weight: bold;">
                    <el-input v-model="formLabelAlign.name" placeholder="请输入任务名称"></el-input>
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
                <!-- 数据集 -->
                <el-form-item label="数据集" prop="dataset_id">
                    <el-select v-model="formLabelAlign.dataset_id" placeholder="请选择数据集" style="width:100%;">
                        <el-option v-for="item in dbList" :key="item.id" :label="item.dataset" :value="item.id">
                        </el-option>
                    </el-select>
                </el-form-item>

                <!-- 模型 -->
                <el-form-item label="模型" prop="model_id">
                    <el-select v-model="formLabelAlign.model_id" placeholder="请选择模型" style="width:100%;">
                        <el-option v-for="item in mpList" :key="item.id" :label="item.name" :value="item.id">
                        </el-option>
                    </el-select>
                </el-form-item>

                <el-form-item label="描述" prop="remark">
                    <el-input v-model="formLabelAlign.remark" placeholder="请输入描述"></el-input>
                </el-form-item>
                <!-- 参数设置 -->
                <!-- <el-form-item label="参数设置" style="font-weight: bold;"></el-form-item>

                <使用 el-row 和 el-col 实现每行两个表单项 >
                <el-row>
                    <el-col :span="11">
                        <el-form-item label="epochs" label-width="108px" prop="epochs">
                            <el-input v-model="formLabelAlign.epochs" placeholder="epochs"></el-input>
                        </el-form-item>
                    </el-col>
                    <el-col :span="13">
                        <el-form-item label="batch_size" label-width="150px" prop="test_batch_size">
                            <el-input v-model="formLabelAlign.test_batch_size" placeholder="batch_size"></el-input>
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
                        <el-form-item label="学习衰减因子" label-width="150px" prop="lr_decay_factor">
                            <el-input v-model="formLabelAlign.lr_decay_factor" placeholder="lr_decay_factor"></el-input>
                        </el-form-item>
                    </el-col>
                </el-row>

                <el-row>
                    <el-col :span="11">
                        <el-form-item label="dropout" label-width="108px" prop="dropout">
                            <el-input v-model="formLabelAlign.dropout" placeholder="dropout"></el-input>
                        </el-form-item>
                    </el-col>
                    <el-col :span="13">
                        <el-form-item label="权重衰减系数" label-width="150px" prop="weight_decay">
                            <el-input v-model="formLabelAlign.weight_decay" placeholder="weight_decay"></el-input>
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
                            <el-input v-model="formLabelAlign.lr_decay_step_size"
                                placeholder="lr_decay_step_size"></el-input>
                        </el-form-item>
                    </el-col>

                </el-row>

                <el-row>
                    <el-col :span="11">
                        <el-form-item label="nheads" label-width="108px" prop="nheads">
                            <el-input v-model="formLabelAlign.nheads" placeholder="nheads"></el-input>
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
                        <el-form-item label="LeakyReLU的负斜率系数" label-width="108px" prop="alpha">
                            <el-input v-model="formLabelAlign.alpha" placeholder="alpha"></el-input>
                        </el-form-item>
                    </el-col>
                </el-row> 
            -->
                <el-form-item label="参数设置">
                    <el-form class="dialog-form-content" :rules="rules" ref="formLabelAlign" label-position="right"
                        label-width="200px" :model="formLabelAlign" size="small">
                        <el-form-item label="epochs" prop="epochs" style="padding-top: 16px;">
                            <el-input v-model="formLabelAlign.epochs" style="width:167px;"></el-input>
                        </el-form-item>
                        <el-form-item label="test_batch_size" prop="test_batch_size">
                            <el-input v-model="formLabelAlign.test_batch_size" style="width:167px;"></el-input>
                        </el-form-item>
                        <el-form-item label="学习率" prop="lr">
                            <el-input v-model="formLabelAlign.lr" style="width:167px;"></el-input>
                        </el-form-item>
                        <el-form-item label="学习衰减因子" prop="lr_decay_factor">
                            <el-input v-model="formLabelAlign.lr_decay_factor" style="width:167px;"></el-input>
                        </el-form-item>
                        <el-form-item label="dropout" prop="dropout">
                            <el-input v-model="formLabelAlign.dropout" style="width:167px;"></el-input>
                        </el-form-item>
                        <el-form-item label="权重衰减系数" prop="weight_decay">
                            <el-input v-model="formLabelAlign.weight_decay" style="width:167px;"></el-input>
                        </el-form-item>
                        <el-form-item label="分类数" prop="num_classes">
                            <el-input v-model="formLabelAlign.num_classes" style="width:167px;"></el-input>
                        </el-form-item>

                        <el-form-item label="学习率衰减步长" prop="lr_decay_step_size">
                            <el-input v-model="formLabelAlign.lr_decay_step_size" style="width:167px;"></el-input>
                        </el-form-item>
                        <el-form-item label="nheads" prop="nheads">
                            <el-input v-model="formLabelAlign.nheads" style="width:167px;"></el-input>
                        </el-form-item>
                        <el-form-item label="隐藏层神经元数量" prop="hidden">
                            <el-input v-model="formLabelAlign.hidden" style="width:167px;"></el-input>
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
                <el-button @click="addVisible = false">取消</el-button>
                <el-button type="primary" @click="handleSubmit()">确认</el-button>
            </span>
        </el-dialog>

        <!-- 编辑-->
        <el-dialog title="" :visible.sync="editVisible">

            <div class="dialog-title">
                <span>测试任务设置</span>
                <i class="el-dialog__headerbtn el-icon-close" @click="editVisible = false"></i>
            </div>

            <el-form class="dialog-content" :rules="rules" ref="formLabelAlign" label-position="right"
                label-width="112px" :model="formLabelAlign" size="small">
                <!-- 任务名称 -->
                <el-form-item label="任务名称" prop="name">
                    <el-input v-model="formLabelAlign.name" placeholder="请输入任务名称"></el-input>
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
                <!-- 数据集 -->
                <el-form-item label="数据集" prop="dataset_id" style="width: 100%;">
                    <el-select v-model="formLabelAlign.dataset_id" placeholder="请选择数据集">
                        <el-option v-for="item in dbList" :key="item.id" :label="item.dataset" :value="item.id">
                        </el-option>
                    </el-select>
                </el-form-item>

                <!-- 模型 -->
                <el-form-item label="模型" prop="model_id" style="width: 100%;">
                    <el-select v-model="formLabelAlign.model_id" placeholder="请选择模型">
                        <el-option v-for="item in mpList" :key="item.id" :label="item.name" :value="item.id">
                        </el-option>
                    </el-select>
                </el-form-item>

                <el-form-item label="描述" prop="remark">
                    <el-input v-model="formLabelAlign.remark" placeholder="请输入描述"></el-input>
                </el-form-item>

                <el-form-item label="参数设置">
                    <el-form class="dialog-form-content" :rules="rules" ref="formLabelAlign" label-position="right"
                        label-width="112px" :model="formLabelAlign" size="small">
                        <el-form-item label="epochs" prop="epochs" style="padding-top: 16px;">
                            <el-input v-model="formLabelAlign.epochs" style="width:167px;"></el-input>
                        </el-form-item>
                        <el-form-item label="test_batch_size" prop="test_batch_size">
                            <el-input v-model="formLabelAlign.test_batch_size" style="width:167px;"></el-input>
                        </el-form-item>
                        <el-form-item label="学习率" prop="lr">
                            <el-input v-model="formLabelAlign.lr" style="width:167px;"></el-input>
                        </el-form-item>
                        <el-form-item label="学习衰减因子" prop="lr_decay_factor">
                            <el-input v-model="formLabelAlign.lr_decay_factor" style="width:167px;"></el-input>
                        </el-form-item>
                        <el-form-item label="dropout" prop="dropout">
                            <el-input v-model="formLabelAlign.dropout" style="width:167px;"></el-input>
                        </el-form-item>
                        <el-form-item label="权重衰减系数" prop="weight_decay">
                            <el-input v-model="formLabelAlign.weight_decay" style="width:167px;"></el-input>
                        </el-form-item>
                        <el-form-item label="分类数" prop="num_classes">
                            <el-input v-model="formLabelAlign.num_classes" style="width:167px;"></el-input>
                        </el-form-item>

                        <el-form-item label="学习率衰减步长" prop="lr_decay_step_size">
                            <el-input v-model="formLabelAlign.lr_decay_step_size" style="width:167px;"></el-input>
                        </el-form-item>
                        <el-form-item label="nheads" prop="nheads">
                            <el-input v-model="formLabelAlign.nheads" style="width:167px;"></el-input>
                        </el-form-item>
                        <el-form-item label="隐藏层神经元数量" prop="hidden">
                            <el-input v-model="formLabelAlign.hidden" style="width:167px;"></el-input>
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
                <el-button @click="editVisible = false">取消</el-button>
                <el-button type="primary" @click="editSubmit()">确认</el-button>
            </span>
        </el-dialog>
    </div>
</template>

<script>
import { getTime } from '@/utils/getTime';
import { get, del, post, put } from '../../../../api/request';
import { schoolInstance2 } from '../../../../axios';
export default {
    data() {
        return {
            tableData: [
                // { id: 1, name: '测试任务', model: 'CodeGraph4CCDetector', dataset: '数据集1', state: '未运行', gpu: '1', time: '2024.08.14' },
                // { id: 2, name: '测试任务', model: 'CodeGraph4CCDetector', dataset: '数据集1', state: '已运行', gpu: '2', time: '2024.08.14' },
                // { id: 3, name: '测试任务', model: 'CodeGraph4CCDetector', dataset: '数据集1', state: '进行中', gpu: '0', time: '2024.08.14' },
            ],
            showTestDisplay: false, // 控制显示的状态
            addVisible: false, // 新增测试任务对话框显示状态
            editVisible: false, // 编辑测试任务对话框显示状态
            formLabelAlign: {         // 表单绑定的数据
                "id": '',
                "name": '',
                "model": '',
                "dataset": '',
                "dataset_id": '',
                "model_id": '',
                "gpu": '',
                "type": '',
                "state": '',
                "remark": '',
                "result": '',
                "time": '',
                "epochs": '',
                "test_batch_size": '',
                "lr": '',
                "lr_decay_factor": '',
                "weight_decay": '',
                "lr_decay_step_size": '',
                "num_classes": '',
                "hidden": '',
                "dropout": '',
                "nheads": '',
                "alpha": '',

            },
            rules: {
                id: [{ required: true, message: '请输入', trigger: 'blur' }],
                name: [{ required: true, message: '请输入', trigger: 'blur' }],
                model_id: [{ required: true, message: '请输入', trigger: 'blur' }],
                dataset_id: [{ required: true, message: '请输入', trigger: 'blur' }],
                type: [{ required: true, message: '请选择', trigger: 'blur' }],
                epochs: [{ required: true, message: '请输入', trigger: 'blur' }],
                test_batch_size: [{ required: true, message: '请输入', trigger: 'blur' }],
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
            dbList: [
                //数据集列表
            ],
            mpList: [
                //模型列表
            ]
        }
    },
    methods: {
        //获取任务列表
        async getTestList() {
            try {
                const response = await get('/test/getAllTest');
                if (response.data.total != 0) {
                    this.tableData = response.data.list
                }

            } catch (error) {
                console.log('请求模型表格数据失败:', error)
            }
        },
        //获取数据集列表
        async getDbList() {
            try {
                const response = await get('/dataset/getAllDatasets');
                if (response.data.total != 0) {
                    this.dbList = response.data.list
                }
                console.log("数据集列表", this.dbList)
            } catch (error) {
                console.log('请求数据集表格数据失败:', error)
            }
        },
        //获取模型列表
        async getModelList() {
            try {
                const response = await get('/modelList/getAllModels');
                this.mpList = response.data.list
            } catch (error) {
                console.log('请求模型表格数据失败:', error)
            }
        },
        //重制表单
        resetForm() {
            this.formLabelAlign = {
                "id": '',
                "name": '',
                "model": '',
                "dataset": '',
                "dataset_id": '',
                "model_id": '',
                "gpu": '',
                "type": '',
                "state": '',
                "remark": '',
                "result": '',
                "time": '',
                "epochs": '',
                "test_batch_size": '',
                "lr": '',
                "lr_decay_factor": '',
                "weight_decay": '',
                "lr_decay_step_size": '',
                "num_classes": '',
                "hidden": '',
                "dropout": '',
                "nheads": '',
                "alpha": '',
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
            console.log("row", row)
            this.$router.push({
                path: `/home/cltestdisplay`,
                query: { row: JSON.stringify(row) }
            });  
            this.showTestDisplay = true; 
        },
        add() {
            this.resetForm()
            this.formLabelAlign = {
                "id": '',
                "name": '',
                "model": '',
                "dataset": '',
                "dataset_id": '',
                "model_id": '',
                "gpu": '',
                "type": '',
                "state": '',
                "remark": '',
                "result": '',
                "time": '',
                "epochs": '100',
                "test_batch_size": '15000',
                "lr": '0.001',
                "lr_decay_factor": '0.5',
                "weight_decay": '0.0005',
                "lr_decay_step_size": '50',
                "num_classes": '2',
                "hidden": '16',
                "dropout": '0.1',
                "nheads": '16',
                "alpha": '0.2',
            };
            this.formLabelAlign.time = getTime()
            this.addVisible = true
        },
        handleSubmit() {
            this.$refs.formLabelAlign.validate((valid) => {
                console.log("model", this.mpList, this.formLabelAlign.model_id)
                if (valid) {
                    console.log('valid', this.formLabelAlign)
                    this.formLabelAlign.state = -1
                    //将model通过model_id对应
                    this.formLabelAlign.model = this.mpList.find(item => item.id == this.formLabelAlign.model_id).name
                    console.log(this.formLabelAlign.model)
                    //将dataset通过dataset_id对应
                    this.formLabelAlign.dataset = this.dbList.find(item => item.id == this.formLabelAlign.dataset_id).dataset

                    post('/test/addTest', this.formLabelAlign)
                        .then(response => {
                            console.log('响应数据:', response);
                            this.addVisible = false;  // 关闭对话框
                            this.getTestList();
                        })


                } else {
                    console.log('error submit!!');
                    return false;
                }
            });
        },
        editSubmit() {
            this.$refs.formLabelAlign.validate((valid) => {
                if (valid) {
                    console.log('valid', this.formLabelAlign)
                    //将state变为未运行
                    this.formLabelAlign.state = -1
                    //将model通过model_id对应
                    this.formLabelAlign.model = this.mpList.find(item => item.id == this.formLabelAlign.model_id).name
                    console.log(this.formLabelAlign.model)
                    //将dataset通过dataset_id对应
                    this.formLabelAlign.dataset = this.dbList.find(item => item.id == this.formLabelAlign.dataset_id).dataset

                    put('/test/updateTest', this.formLabelAlign)
                        .then(response => {
                            console.log('响应数据:', response);
                            this.editVisible = false;
                            this.getTestList();
                        })


                } else {
                    console.log('error submit!!');
                    return false;
                }
            });
        },
        edit(row) {
            this.editVisible = true
            this.resetForm()
            this.formLabelAlign = { 
                "id": row.id,
                "name": row.name || '无',
                "mode": row.model || '无',
                "dataset": row.dataset || '无',
                "model_id": row.model_id || '无',
                "dataset_id": row.dataset_id || '无',
                "gpu": '',
                "state": row.state || '',
                "type": row.type || '',
                "param": row.parma || '',
                "remark": row.remark || '',
                "result": row.result || '',
                "time": row.time || '无',
                "epochs": row.epochs || '100',
                "test_batch_size": row.test_batch_size || '15000',
                "lr": row.lr || '0.001',
                "lr_decay_factor": row.lr_decay_factor || '0.5',
                "weight_decay": row.weight_decay || '0.0005',
                "lr_decay_step_size": row.lr_decay_step_size || '50',
                "num_classes": row.num_classes || '2',
                "hidden": row.hidden || '16',
                "dropout": row.dropout || '0.1',
                "nheads": row.nheads || '16',
                "alpha": row.alpha || '0.2',

            };
        },
        del(row) {
            console.log(row)
            this.$confirm('是否删除当前数据?', '提示', {
                confirmButtonText: '确定',
                cancelButtonText: '取消',
                type: 'warning'
            }).then(async () => {
                try {
                    const response = await del(`/test/deleteTest/${row.id}`)
                    console.log("response", response)
                    //删除tableData中数据
                    if (response.errmsg === "成功") {
                        this.tableData = this.tableData.filter(item => item.id !== row.id)
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
        }
    },
    mounted() {
        // 当组件挂载时，自动调用
        this.getModelList();
        this.getDbList();
        this.getTestList();
    }
}
</script>

<style scoped>
@import"../../../../css/testTask.css";
</style>