<template>
    <div>
        <div class="index">
            <div class="head">
                <div class="bkcl">
                    <div class="progress1">
                        <el-progress class="custom-progress1" :text-inside="true" :stroke-width="32" :percentage="70"
                            text-color="rgb(0,0,0)"></el-progress>
                    </div>
                    <div class="buttons">
                        <el-button type="primary" size="mini" :disabled="this.tList.state != '-1'"
                            @click="open()">启动</el-button>
                        <el-button type="danger" size="mini" :disabled="this.tList.state != '-1'"
                            @click="stop()">停止</el-button>
                    </div>
                </div>
            </div>
            <div class="contral">
                <p class="a1-pre">
                    运行界面</p>
                <el-divider class="divider"></el-divider>
                <!-- <div class="a2-pre"></div> -->
                <div class="a2-pre" v-html="content"></div>
            </div>

        </div>

        <!--选择GPU界面 -->
        <div>
            <el-dialog title="" :visible.sync="gpuChoose" width="500px">

                <div class="dialog-title">
                    <span>选择GPU</span>
                    <i class="el-dialog__headerbtn el-icon-close" @click="gpuChoose = false"></i>
                </div>

                <el-form :rules="rules" ref="tList" label-position="right" label-width="100px" :model="tList"
                    size="small">
                    <el-form-item label="使用GPU" style="font-weight: bold;" label-width="103px" prop="gpu">
                        <el-radio-group v-model="tList.gpu">
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
import { post, get, put } from '../../../api/request';
import { schoolInstance2, schoolInstance6 } from '../../../axios';

export default {
    computed: {

    },
    created() {
        //获取父组件传递内容
        const row = JSON.parse(this.$route.query.row);
        this.tList = row
    },
    data() {
        return {
            news: [
                {
                    content: `
                     Train list: 353398<br>
                        Valid list: 13695<br>
                        Test list: 13695<br>
                        Add all data to RAM...<br>
                        Test list 27390<br>
                        Test list 166<br>
                        0it [00:00, ?it/s]<br>
                        100%|██████████████████████████████████████████████████████████████████████████████████████████████████████████████| 242/242 [00:02<00:00, 112.88it/s]<br>
                        100%|████████████████████████████████████████████████████████████████████████████████████████████████████████████████| 27/27 [00:00<00:00, 623.63it/s]<br>
                        100%|█████████████████████████████████████████████████████████████████████████████████████████████████████████████| 18/18 [00:00<00:00, 155344.59it/s]<br>
                        100%|███████████████████████████████████████████████████████████████████████████████████████████████████████████████| 68/68 [00:00<00:00, 1194.10it/s]<br>
                        100%|██████████████████████████████████████████████████████████████████████████████████████████████████████████████| 245/245 [00:00<00:00, 647.09it/s]<br>
                        100%|████████████████████████████████████████████████████████████████████████████████████████████████████████████████| 2/2 [00:00<00:00, 19925.43it/s]<br>
                        100%|████████████████████████████████████████████████████████████████████████████████████████████████████████████████| 4/4 [00:00<00:00, 27594.11it/s]<br>
                        100%|█████████████████████████████████████████████████████████████████████████████████████████████████████████████| 478/478 [00:00<00:00, 1143.89it/s]<br>
                        100%|█████████████████████████████████████████████████████████████████████████████████████████████████████████████| 435/435 [00:00<00:00, 1607.31it/s]<br>
                        100%|█████████████████████████████████████████████████████████████████████████████████████████████████████████████| 20/20 [00:00<00:00, 186828.69it/s]<br>
                        100%|███████████████████████████████████████████████████████████████████████████████████████████████████████████████| 88/88 [00:00<00:00, 1600.41it/s]<br>
                        100%|████████████████████████████████████████████████████████████████████████████████████████████████████████████████| 38/38 [00:00<00:00, 648.56it/s]<br>
                        157 5<br>
                        Model index: 1<br>
                        Batch size: 15000<br>
                        Save graphEmbDict...<br>
                        0%|| 0/157 [00:00<?, ?it/s]/home/cz/miniconda3/envs/py3.8/lib/python3.8/site-packages/torch_geometric/deprecation.py:26: UserWarning: 'nn.glob.GlobalAttention' is deprecated, use 'nn.aggr.AttentionalAggregation' instead<br>
                        warnings.warn(out)<br>
                        100%|███████████████████████████████████████████████████████████████████████████████████████████████████████████████| 157/157 [00:07<00:00, 19.91it/s]<br>
                        Test (p_a=0.9836,r_a=0.9859,f_a=0.9848): 100%|██████████████████████████████████████████████████████████████████████████| 1/1 [00:04<00:00,  4.32s/it]<br>
                        testCount 12246<br>
                        notFound 1449<br>
                        test speed time: 4.315778017044067<br>
                `
                }
            ],
            gpuList: [],
            gpuChoose: false, //gpu选择页面
            tList: '',//传参存储
            intervalId: null,//定时器
            rules: {
                gpu: [{ required: true, message: '请选择', trigger: 'blur' }],
            },
            gpuProgress: [], // 各 GPU 的进度值
            customColor: '#409eff',//gpu进度颜色
            progress: 0,//进度条进度
            jsonData: {},
            content: '',//控制台内容
        }
    },
    methods: {
        async open() {
            this.gpuChoose = true;
        },
        stop() {
            const jsonData = {
                id: this.tList.id
            };
            post('/stop_process', JSON.stringify(jsonData), schoolInstance6)
        },
        async getGpuUsed() {
            try {
                const response = await schoolInstance2.get('/gpu_usage'); // 使用实例的 get 方法
                this.gpuList = Object.values(response); // 确保将响应中的数据正确存储到 gpuList
                this.gpuList.forEach((gpu, index) => {
                    this.gpuProgress[index] = parseFloat(gpu.memory_utilization_percent);
                })
            } catch (error) {
                console.error('请求错误:', error); 
            }
        },
        gpuSubmit() {
            this.$refs.tList.validate((valid) => {
                if (valid) {
                    put('/preprocessing/updatePreprocessing', JSON.stringify(this.tList))
                        .then(response => {
                            this.gpuChoose = false;  
                            this.jsonData.id = this.tList.id
                            const jsonData2 = JSON.stringify(this.jsonData); 
                            return post('/process', jsonData2, schoolInstance6);
                        })
                    // 启动定时器获取数据
                    this.intervalId = setInterval(async () => {
                        const result = await this.getPreList();
                    }, 2000);
                }
            })
        },
        isDisabled(gpuIndex) {
            const disabledGpus = []; 
            for (const gpu of this.gpuList) {
                const memoryUtilization = parseFloat(gpu["memory_utilization_percent"]);

                if (memoryUtilization >= 70) {
                    disabledGpus.push(gpu["GPU_index"]); 
                }
            }
            return disabledGpus.includes(gpuIndex);

        },
        async getPreList() {
            try {
                // const jsonData2 = JSON.stringify(this.tList.id);
                const response = await get(`/preprocessing/getPreprocessingById/${this.tList.id}`);
                if (response.data.result) {
                    const content = `${response.data.result
                        .trim() // 去除首尾空白
                        .split('\n') // 按行拆分
                        .join('<br>')}`; // 用 <br> 连接每行
                    // 找到匹配的任务，更新表格数据
                    this.content = content;
                    this.scrollToBottom();

                }
            } catch (error) {
                console.log('请求模型表格数据失败:', error)
            }
        },
        //点击查看进来时，触发的操作
        startInterval() {
            this.intervalId = setInterval(async () => {
                const result = await this.getPreList();
            }, 2000);
        },
        scrollToBottom() {
            const container = document.querySelector('.a2');
            if (container) {
                container.scrollTop = container.scrollHeight; // 设置滚动条到最底部
            }
        },
    },
    mounted() {
        this.getGpuUsed();
        if (this.tList.state == '0') {
            this.startInterval()
        } else if (this.tList.state == '1') {
            this.getPreList()
        }
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
@import"../../../css/preDisplay.css";
</style>