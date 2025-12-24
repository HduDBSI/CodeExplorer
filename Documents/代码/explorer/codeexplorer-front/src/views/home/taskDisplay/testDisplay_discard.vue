<template>
    <div class="index">
        <div class="head">
            <div class="progress">
                <el-progress :text-inside="true" :stroke-width="26" :percentage="90"
                    text-color="rgb(250, 250, 250)"></el-progress>
            </div>
            <div class="buttons">
                <el-button type="primary" size="mini" @click="open()">启动</el-button>
                <el-button type="danger" size="mini">停止</el-button>
            </div>
        </div>
        <div>
            <el-table :data="parameters" style="width: 100%; " :cell-style="getCellClassName">
                <el-table-column label="参数设置" width="106" align="right">
                    <template slot-scope="scope">
                        <div>{{ scope.row.key1 }}</div>
                    </template>
                </el-table-column>
                <el-table-column prop="value1" label="" width="auto" align="left">
                </el-table-column>

                <el-table-column label="" width="131" align="right">
                    <template slot-scope="scope">
                        <div class="header-style">{{ scope.row.key2 }}</div>
                    </template>
                </el-table-column>
                <el-table-column prop="value2" label="" width="auto" align="left">
                </el-table-column>

                <el-table-column label="" width="100" align="right">
                    <template slot-scope="scope">
                        <div class="header-style">{{ scope.row.key3 }}</div>
                    </template>
                </el-table-column>
                <el-table-column prop="value3" label="" width="auto" align="left">
                </el-table-column>

                <el-table-column label="" width="180" align="right">
                    <template slot-scope="scope">
                        <div class="header-style">{{ scope.row.key4 }}</div>
                    </template>
                </el-table-column>
                <el-table-column prop="value4" label="" width="auto" align="left">
                </el-table-column>

                <el-table-column label="" width="140" align="right">
                    <template slot-scope="scope">
                        <div class="header-style">{{ scope.row.key5 }}</div>
                    </template>
                </el-table-column>
                <el-table-column prop="value5" label="" width="auto" align="left">
                </el-table-column>

                <el-table-column label="" width="100" align="right">
                    <template slot-scope="scope">
                        <div class="header-style">{{ scope.row.key6 }}</div>
                    </template>
                </el-table-column>
                <el-table-column prop="value6" label="" width="auto" align="left">
                </el-table-column>
            </el-table>
        </div>
        <div class="contral">
            <el-tabs class="a1" v-model="activeName" @tab-click="handleClick">
                <el-tab-pane label="运行界面" name="first"></el-tab-pane>
                <el-tab-pane label="运行结果" @click="clean()" name="second"></el-tab-pane>
            </el-tabs>

            <div class="a2" v-if="activeName === 'first'">
                <div class="a2"></div>
            </div>
            <div v-else-if="activeName === 'second'">
                <div v-if="state == true">
                    <el-collapse v-model="activeNames">
                        <el-collapse-item v-for="item in result" :key="item.id" :title="item.title" :name="item.id">
                            <div class="collapse-content">
                                <div class="left-content">
                                    <pre v-highlight><code>{{ item.content1 }}</code></pre>
                                </div>
                                <div class="center-content"></div>
                                <div class="right-content">
                                    <pre v-highlight><code>{{ item.content2 }}</code></pre>
                                </div>
                            </div>
                        </el-collapse-item>
                    </el-collapse>
                </div>
                <div v-else-if="state == false">
                    <p>无运行结果</p>
                </div>
            </div>

        </div>

    </div>
</template>

<script>
import { add, post, del } from '../../../api/request';
import { mapGetters, mapActions } from 'vuex';
import { schoolInstance1 } from '../../../axios';

export default {
    computed: {
        ...mapGetters(['globalVariable']),

    },
    data() {
        return {
            code: `
                     <div class="a2">
                        <div v-if="activeName === 'first'">
                        <!-- 运行界面内容 -->
                        <p id="run">这里是运行界面的内容。</p>
                    </div>
                    <div v-else-if="activeName === 'second'">
                        <!-- 运行结果内容 -->
                        <p id="result">这里是运行结果的内容。</p>
                    </div>
                    `,

            parameters: [
                { key1: 'epochs :', value1: '100', key2: 'test_batch_size :', value2: '15000', key3: 'lr :', value3: '0.001', key4: 'lr_decay_factor :', value4: '0.5', key5: 'weight_decay :', value5: '0.0005', key6: 'dropout :', value6: '0.1' },
                { key1: 'hidden :', value1: '16', key2: 'num_classes :', value2: '2', key3: 'nheads :', value3: '16', key4: 'lr_decay_step_size :', value4: '50', key5: 'alpha :', value5: '0.2', key6: '', value6: '' },
            ],
            news: [
                {
                    id: 1,
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
                },

            ],
            result: [
                {
                    id: 1,
                    title: '代码片段1  代码片段2',
                    content1: `
                    <div class="a2">
                        <div v-if="activeName === 'first'">
                        <!-- 运行界面内容 -->
                        <p id="run">这里是运行界面的内容。</p>
                    </div>
                    <div v-else-if="activeName === 'second'">
                        <!-- 运行结果内容 -->
                        <p id="result">这里是运行结果的内容。</p>
                    </div>
                    `,
                    content2: `
                    <div class="a2">
                        <div v-if="activeName === 'first'">
                        <p id="run">这里是运行界面的内容。</p>
                    </div>
                    <div v-else-if="activeName === 'second'">
                        <p id="result">这里是运行结果的内容。</p>
                    </div>
                    `
                },
                {
                    id: 2,
                    title: '代码片段3  代码片段4',
                    content1:
                        `
        open() {
            this.fetchApiContent(this.postData)
            .then(content => {
                const container = document.querySelector('#run');
                container.innerHTML = content;
            })
            .catch(error => {
                console.error('请求错误:', error);
            });
        },
        getCellClassName({ row }) {
            return 'padding-right: 0 !important;'; // 对所有单元格应用这个类
        },
        handleClick(tab, event) {
            console.log(tab, event);  // 你可以在这里添加点击时的其他逻辑
        }

    },
    created() {
        // 自动加载内容，如果已存在
        if (this.apiContent) {
            const container = document.querySelector('#run');
            container.innerHTML = this.apiContent;
        }
        //获取父组件传递内容
        const tList = JSON.parse(this.$route.query.row);
    }
                    `,
                    content2: `
        open() {
            this.fetchApiContent(this.postData)
                .then(content => {
                    const container = document.querySelector('#run');
                    container.innerHTML = content;
                })
                .catch(error => {
                    console.error('请求错误:', error);
                });
        },
        getCellClassName({ row }) {
            return 'padding-right: 0 !important;'; // 对所有单元格应用这个类
        },
        handleClick(tab, event) {
            console.log(tab, event);  // 你可以在这里添加点击时的其他逻辑
        }

    },
                    `
                }
            ],
            postData: {
                "epochs": 1,
                "batch_size": 64,
                "test_batch_size": 15000,
                "lr": 0.0001,
                "lr_decay_factor": 0.5,
                "weight_decay": 0.0005,
                "lr_decay_step_size": 50,
                "hidden": 16,
                "num_classes": 2,
                "nheads": 16,
                "dropout": 0.1,
                "alpha": 0.2,

            },

            activeName: 'first',
            activeNames: [],
            state: false,
            container:[]
        }
    },

    methods: {
        ...mapActions(['updateGlobalVariable']),
        open() {
            // 将数据转换为 JSON 字符串
            const result1 = post('/test', this.postData, schoolInstance1)
                .then(response => {
                    const content = `${response
                        .trim() // 去除首尾空白
                        .split('\n') // 按行拆分
                        .join('<br>')}`; // 用 <br> 连接每行
                    this.updateGlobalVariable(content);
                     container = document.querySelector('.a2');
                    container.innerHTML = this.globalVariable;
                })
                .catch(error => {
                    console.error('请求错误:', error);
                });
        },
        clean() {
            this.state = true; 
            this.activeName = 'second'; 
            this.container = []
            location.reload(); 

        },
        getCellClassName({ row }) {
            return 'padding-right: 0 !important;'; // 对所有单元格应用这个类
        },
        handleClick(tab, event) {
            this.activeName = tab.name;
        }

    },
    created() {
        const tList = JSON.parse(this.$route.query.row);
        if (tList.state === '已运行') {
            this.state = true
        } else {
            this.state = false
        }
    }
}
</script>

<style scoped>
@import"../../../../css/testDisplay.css";
</style>