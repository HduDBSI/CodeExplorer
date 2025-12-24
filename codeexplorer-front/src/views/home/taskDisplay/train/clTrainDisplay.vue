<template>
    <div>
        <div class="index">
            <div class="bk">
                <div class="head">
                    <div class="bkcl">
                        <div class="progress1">
                            <el-progress class="custom-progress1" :text-inside="true" :stroke-width="32" :percentage="70"
                                text-color="rgb(0,0,0)"></el-progress>
                        </div>
                        <div class="buttons">
                            <el-button type="primary" size="mini" :disabled="this.rowData.state != '-1'"
                                @click="open()">启动</el-button>
                            <el-button type="danger" size="mini" :disabled="this.rowData.state != '-1'"
                                @click="stop()">停止</el-button>
                        </div>
                    </div>
                </div>

                <span class="sptitle">参数设置</span>
                <el-table :data="parameters" :show-header="false" class="tbst2">
                    <el-table-column label="" width="131" align="right">
                        <template slot-scope="scope">
                            <div class="header-style">{{ scope.row.key2 }}</div>
                        </template>
                    </el-table-column>
                    <el-table-column prop="value2" label="" width="auto">
                    </el-table-column>

                    <el-table-column label="" width="100" align="right">
                        <template slot-scope="scope">
                            <div class="header-style">{{ scope.row.key3 }}</div>
                        </template>
                    </el-table-column>
                    <el-table-column prop="value3" label="" width="auto">
                    </el-table-column>

                    <el-table-column label="" width="180" align="right">
                        <template slot-scope="scope">
                            <div class="header-style">{{ scope.row.key4 }}</div>
                        </template>
                    </el-table-column>
                    <el-table-column prop="value4" label="" width="auto">
                    </el-table-column>

                    <el-table-column label="" width="140" align="right">
                        <template slot-scope="scope">
                            <div class="header-style">{{ scope.row.key5 }}</div>
                        </template>
                    </el-table-column>
                    <el-table-column prop="value5" label="" width="auto">
                    </el-table-column>

                    <el-table-column label="" width="100" align="right">
                        <template slot-scope="scope">
                            <div class="header-style">{{ scope.row.key6 }}</div>
                        </template>
                    </el-table-column>
                    <el-table-column prop="value6" label="" width="auto">
                    </el-table-column>
                </el-table>
            </div>

            <div class="contral">
                <p class="a1">
                    运行界面
                </p>
                <el-divider class="divider"></el-divider>
                <div class="a2" v-html="content"></div>
            </div>
        </div>

        <!--选择GPU界面 -->
        <div>
            <el-dialog title="" :visible.sync="gpuChoose" width="500px" >
                <div class="dialog-title">
                    <span>选择GPU</span>
                    <i class="el-dialog__headerbtn el-icon-close" @click="gpuChoose = false"></i>
                </div>
                <el-form :rules="rules" ref="rowData" label-position="right" label-width="100px" :model="rowData"
                    size="small">
                    <el-form-item label="使用GPU" style="font-weight: bold;" label-width="103px" prop="gpu">
                        <el-radio-group v-model="rowData.gpu">
                            <el-radio :label="0" :key="0" :disabled="gpuProgress[0] >= 40">GPU 0
                                <el-progress class="gpu" :percentage="gpuProgress[0]"
                                    :color="customColor"></el-progress>

                            </el-radio>
                            <div style="margin: 15px 0;"></div>
                            <el-radio :label="1" :key="1" :disabled="gpuProgress[1] >= 40">GPU 1
                                <el-progress class="gpu" :percentage="gpuProgress[1]"
                                    :color="customColor"></el-progress>
                            </el-radio>

                            <div style="margin: 15px 0;"></div>
                            <el-radio :label="2" :key="2" :disabled="gpuProgress[2] >= 40">GPU 2
                                <el-progress class="gpu" :percentage="gpuProgress[2]"
                                    :color="customColor"></el-progress>
                            </el-radio>
                            <div style="margin: 15px 0;"></div>
                            <el-radio :label="3" :key="3" :disabled="gpuProgress[3] >= 40">GPU 3
                                <el-progress class="gpu" :percentage="gpuProgress[3]"
                                    :color="customColor"></el-progress>
                            </el-radio>
                        </el-radio-group>
                    </el-form-item>
                </el-form>
                <span slot="footer" class="dialog-footer">
                    <el-button @click="gpuChoose = false">取消</el-button>
                    <el-button type="primary" @click="gpuSubmit()">确认</el-button>
                </span>
            </el-dialog>
        </div>
    </div>
</template>

<script>
// import axiosInstance from '../../../axios'
import { add, post, get, put } from '../../../../api/request';
import { schoolInstance3, schoolInstance2 } from '../../../../axios';

export default {

    created() {
        //获取父组件传递内容
        const row = JSON.parse(this.$route.query.row);
        this.rowData = row
        console.log("rowData", this.rowData)
    },
    mounted() {
        this.getGpuUsed();
        this.updateParameters();
        if (this.rowData.state == '0') {
            this.startInterval()
        } else if (this.rowData.state == '1') {
            this.getTrainList()
        }
    },
    data() {
        return {
            parameters: [
                { key1: 'epochs :', value1: '', key2: 'batch_size :', value2: '', key3: 'lr :', value3: '', key4: 'lr_decay_factor :', value4: '', key5: 'weight_decay :', value5: '', key6: 'dropout :', value6: '' },
                { key1: 'hidden :', value1: '', key2: 'num_classes :', value2: '', key3: 'nheads :', value3: '', key4: 'lr_decay_step_size :', value4: '', key5: 'alpha :', value5: '' },
            ],

            content: '',
            rowData: {},
            intervalId: null, // 定时器ID
            gpuList: [],
            gpuChoose: false,
            gpuProgress: [], // 各 GPU 的进度值
            customColor: '#409eff',//gpu进度颜色
            progress: 0,//进度条进度
            rules: {
                gpu: [{ required: true, message: '请选择', trigger: 'blur' }],
            }

        }
    },
    methods: {
        open() {
            this.gpuChoose = true;
        },
        stop() {
            const jsonData = {
                id: this.rowData.id
            };
            post('/stop_train', JSON.stringify(jsonData), schoolInstance3)
        },
        startInterval() {
            this.intervalId = setInterval(async () => {
                const result = await this.getTrainList();

            }, 2000);
        },
        //获取训练结果
        async getTrainList() {
            try {
                const response = await get('/train/getAllTrain');
                if (response.data.total != 0) {
                    for (const item of response.data.list) {
                        if (item.id == this.rowData.id) {
                            if (item.result) {
                                const content = `${item.result
                                    .trim() // 去除首尾空白
                                    .split('\n') // 按行拆分
                                    .join('<br>')}`; // 用 <br> 连接每行
                                this.content = content;
                                this.scrollToBottom(); 
                                break; 
                            }
                        }
                    }
                }

            } catch (error) {
                console.log('请求模型表格数据失败:', error)
            }
        },
        //获取gpu和内存使用率
        async getGpuUsed() {
            try {
                const response = await schoolInstance2.get('/gpu_usage');
                this.gpuList = Object.values(response);
                this.gpuList.forEach((gpu, index) => {
                    this.gpuProgress[index] = parseFloat(gpu.memory_utilization_percent);
                })

            } catch (error) {
                console.error('请求错误:', error); 
            }
        },
        gpuSubmit() {
            this.$refs.rowData.validate((valid) => {
                if (valid) {
                    put('/train/updateTrain', this.rowData)
                        .then(response => {
                            console.log('响应数据:', response);
                            this.gpuChoose = false;  
                            const jsonData = JSON.stringify(this.rowData); 
                            console.log("this.rowData", this.rowData)
                            return post('/train', jsonData, schoolInstance3)
                        })
                    // 启动定时器获取数据
                    this.intervalId = setInterval(async () => {
                        const result = await this.getTrainList();
                        get(`/train/getTrainById/${this.rowData.id}`)
                            .then(state => {
                                console.log('state', state.data.state);
                                if (state.data.state == 1) {
                                    clearInterval(this.intervalId); 
                                }
                            })
                    }, 2000);
                }
            })
        },
        isDisabled(gpuIndex) {
            const disabledGpus = []; 
            for (const gpu of this.gpuList) {

                const memoryUtilization = parseFloat(gpu["memory_utilization_percent"]);

                if (memoryUtilization >= 40) {
                    disabledGpus.push(gpu["GPU_index"]); 

                }
            }
            return disabledGpus.includes(gpuIndex);

        },
        updateParameters() {
            console.log("this.rowData", this.rowData)
            // 遍历 parameters 数组并更新其值
            this.parameters.forEach(paramGroup => {
                Object.keys(paramGroup).forEach(key => {
                    if (key.startsWith('value')) {
                        const keyIndex = key.replace('value', 'key');
                        const paramKey = paramGroup[keyIndex];
                        if (paramKey && this.rowData[paramKey.replace(' :', '')] !== undefined) {
                            paramGroup[key] = this.rowData[paramKey.replace(' :', '')];
                        }
                    }
                });
            });
        },

        scrollToBottom() {
            const container = document.querySelector('.a2');
            if (container) {
                container.scrollTop = container.scrollHeight;
            }
        },
    },
    beforeDestroy() {
        // 停止定时器
        if (this.intervalId) {
            clearInterval(this.intervalId);
        }
    },

}
</script>

<style scoped>
@import"../../../../css/trainDisplay.css";
</style>