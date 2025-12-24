<template>
    <div>
        <!--总体界面 -->
        <div class="index">
            <div class="bk">
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

                <div>
                    <span class="sptitle">参数设置</span>
                    <el-table class="tbst2" :data="parameters"  :show-header="false" :cell-style="getCellClassName">

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
            </div>
            <div class="contral">
                <el-tabs class="a1" v-model="activeName" @tab-click="handleClick">
                    <el-tab-pane label="运行界面" name="first"></el-tab-pane>
                    <el-tab-pane label="运行结果" name="second"></el-tab-pane>
                </el-tabs>

                <div class="a2" v-if="activeName === 'first'">
                    <!-- 运行界面内容 -->
                    <div v-html="content1"></div>
                </div>
                <div class="a3" v-else-if="activeName === 'second'">
                    <div v-if="state === true" class="bigDiv">
                        <!-- 运行结果内容 -->
                        <el-row type="flex" :gutter="4" justify="space-between">
                            <el-col :span="5" class="bg-purple-left ">
                                <div v-if="fileTree1.length" class="fileTree">
                                    <h3>文件结构:</h3>
                                    <ul class="fileStyle">
                                        <li v-for="(file, index) in fileTree1" :key="index">
                                            <span @click="file.isOpen = !file.isOpen" style="cursor: pointer;">
                                                <span v-if="file.isDirectory" class="arrow">{{ file.isOpen ? ' ▼' :
                                                    ' ►'
                                                    }}</span>
                                                {{ file.name }}
                                            </span>
                                            <ul v-if="file.isDirectory && file.isOpen">
                                                <li v-for="(child, idx) in file.files" :key="idx">
                                                    <span @click="child.isOpen = !child.isOpen"
                                                        style="cursor: pointer;">
                                                        <span v-if="child.isDirectory" class="arrow">{{ child.isOpen
                                                            ?
                                                            '▼'
                                                            :
                                                            '►' }}</span>
                                                        {{ child.name }}
                                                    </span>
                                                    <ul v-if="child.isDirectory && child.isOpen">
                                                        <li v-for="(subChild, subIdx) in child.files" :key="subIdx">
                                                            <!-- {{ subChild.name }} -->
                                                            <span :style="{
                                                                color: subChild.isClone ? 'red' : 'inherit',
                                                                cursor: subChild.isClone ? 'pointer' : 'default'
                                                            }" @click="show(subChild)">{{
                                                                    subChild.name
                                                                }}</span>
                                                        </li>
                                                    </ul>
                                                </li>
                                            </ul>
                                        </li>
                                    </ul>
                                </div>
                            </el-col>
                            <el-col :span="8">
                                <div class="grid-content bg-purple ">
                                    <pre v-highlight><code class="center1"></code></pre>
                                </div>
                            </el-col>
                            <el-col :span="8">
                                <div class="grid-content bg-purple ">
                                    <pre v-highlight><code class="center2"></code></pre>
                                </div>
                            </el-col>
                            <el-col :span="5">
                                <div class="grid-content bg-purple-light right">
                                    <div v-if="fileTree2.length" class="fileTree">
                                        <h3>克隆代码所在目录:</h3>
                                        <ul class="fileStyle">
                                            <li v-for="(file, index) in fileTree2" :key="index">
                                                <span>
                                                    <span v-if="file.isDirectory" class="arrow">{{ file.isOpen ? '▼' :
                                                        ' ►'
                                                        }}</span>
                                                    {{ file.name }}
                                                </span>
                                                <ul v-if="file.isDirectory && file.isOpen">
                                                    <li v-for="(child, idx) in file.files" :key="idx">
                                                        <span>
                                                            <span v-if="child.isDirectory" class="arrow">{{
                                                                child.isOpen
                                                                    ?
                                                                    '▼' : '►'
                                                            }}</span>
                                                            {{ child.name }}
                                                        </span>
                                                        <ul v-if="child.isDirectory && child.isOpen">
                                                            <li v-for="(subChild, subIdx) in child.files" :key="subIdx">
                                                                <!-- {{ subChild.name }} -->
                                                                <span
                                                                    :style="{ color: subChild.isClone ? 'red' : 'inherit' }"
                                                                    @click="show(subChild)">{{
                                                                        subChild.name
                                                                    }}</span>
                                                            </li>
                                                        </ul>
                                                    </li>
                                                </ul>
                                            </li>
                                        </ul>
                                    </div>
                                </div>
                            </el-col>
                        </el-row>
                    </div>
                    <div v-else-if="state === false">
                        <p>无运行结果</p>
                    </div>
                </div>
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
import { add, post, del, get, put } from '../../../../api/request';
import { mapGetters, mapActions } from 'vuex';
import { schoolInstance2, schoolInstance1, schoolInstance4, schoolInstance5, schoolInstance8 } from '../../../../axios';


export default {

    mounted() {
        this.getGpuUsed();
        console.log("rowData", this.tList);
        this.updateParameters();
        if (this.tList.state == '0') {
            this.startInterval()
        } else if (this.tList.state == '1') {
            this.getTestList()
        }
    },
    computed: {
        ...mapGetters(['globalVariable']),

    },
    data() {
        return {
            parameters: [
                { key1: 'epochs:', value1: '', key2: 'test_batch_size:', value2: '', key3: 'lr:', value3: '', key4: 'lr_decay_factor:', value4: '', key5: 'weight_decay:', value5: '', key6: 'dropout:', value6: '' },
                { key1: 'hidden:', value1: '', key2: 'num_classes:', value2: '', key3: 'nheads:', value3: '', key4: 'lr_decay_step_size:', value4: '', key5: 'alpha:', value5: '', key6: '', value6: '' },
            ],
            textResult: [
                {
                    "code1_path": "googlejam4_src/2/googlejam3.p449.A.java",
                    "code1_content": "package googlejam3.p449;\nimport java.io.BufferedReader;\nimport java.io.File;\nimport java.io.FileReader;\nimport java.io.IOException;\n\npublic class A {\n\tpublic static void main(String[] args) {\n\t\tA obj = new A();\n\t\tString filame = \"input.txt\";\n\t\tif (args.length > 0) {\n\t\t\tfilame = args[0];\n\t\t}\n\t\tFile file = new File(filame);\n\t\ttry (BufferedReader br = new BufferedReader(new FileReader(file))) {\n\t\t\tInteger testCasesCount = Integer.parseInt(br.readLine());\n\t\t\tint testCaseNumber = 1;\n\t\t\tfor (String line; (line = br.readLine()) != null;) {\n\t\t\t\tString[] testCase = line.split(\"\\\\s+\");\n\t\t\t\tint row = Integer.parseInt(testCase[0]);\n\t\t\t\t\t\tint cells = Integer.parseInt(testCase[1]);\n\t\t\t\t\t\tint width = Integer.parseInt(testCase[2]);\n\t\t\t\t//\t\tchar[] visitors = visitorsLine.toCharArray();\n\t\t\t\t//\t\tint total = 0;\n\t\t\t\t//\t\tint friends = 0;\n\t\t\t\t//\n\t\t\t\t//\t\tSystem.out.print(\"Case #\" + testCasesCount + \": \");\n\t\t\t\t//\t\tfor (int i = 0; i < visitors.length; i++) {\n\t\t\t\t//\t\t\tint shameCount = Character.getNumericValue(visitors[i]);\n\t\t\t\t//\n\t\t\t\t//\t\t\tif (total < i) {\n\t\t\t\t//\n\t\t\t\t//\t\t\t\tint frindsToAdd = i - total;\n\t\t\t\t//\t\t\t\ttotal += frindsToAdd;\n\t\t\t\t//\t\t\t\tfriends += frindsToAdd;\n\t\t\t\t//\t\t\t}\n\t\t\t\t//\t\t\ttotal += shameCount;\n\t\t\t\t//\t\t}\n\t\t\t\t\t\t//System.out.print(row);\n\t\t\t\t\t\t//System.out.print(cells);\n\t\t\t\t\t\t//System.out.println(width);\n\t\t\t\t\n\t\t\t\t\t\tint limit =  cells / width;\n\t\t\t\t\t\tint res = limit + width-1;\n\t\t\t\t\t\t\n\t\t\t\t\t\tint fin = res * row;\n\t\t\t\t\t\tint left = cells % width;\n\t\t\t\t\t\t//System.out.println(left);\n\t\t\t\t\t\tif(left != 0 && width != 1){\n\t\t\t\t\t\t\tfin++;\n\t\t\t\t\t\t}\n\t\t\t\t\t\t\n\t\t\t\t\t\tSystem.out.println(\"Case #\"+ testCaseNumber++ +\": \"+ fin);\n\t\t\t\t\t\t//System.out.println();\n\t\t\t}\n\t\t} catch (IOException e) {\n\t\t\te.printStackTrace();\n\t\t}\n\n\t}\n}\n",
                    "code2_path": "googlejam4_src/3/googlejam3.p443.A.java",
                    "code2_content": "package googlejam3.p443;\nimport java.io.BufferedReader;\nimport java.io.IOException;\nimport java.io.InputStreamReader;\n\n\npublic class A {\n\tpublic static void main(String[] args) throws IOException {\n\t\tBufferedReader in = new  BufferedReader(new InputStreamReader(System.in));\n\t\t\n\t\tint t = Integer.parseInt(in.readLine());\n\t\t\n\t\tfor (int i = 1; i <= t; i++) {\n\t\t\tint res = 0;\n\t\t\t// body of the program\n\t\t\t\n\t\t\tString[] next = in.readLine().split(\" \");\n\t\t\tint r = Integer.parseInt(next[0]);\n\t\t\tint c = Integer.parseInt(next[1]);\n\t\t\tint w = Integer.parseInt(next[2]);\n\t\t\t\n\t\t\tint row = (c - w) / w;\n\t\t\tif (c % w == 0) row += w;\n\t\t\telse row += w + 1;\n\t\t\t\n\t\t\tres = r*row;\n\t\t\t\n\t\t\tSystem.out.format(\"Case #%d: %d\\n\", i, res);\n\t\t}\n\t}\n}\n",
                    "label": 1
                },
                {
                    "code1_path": "googlejam4_src/3/googlejam3.p211.P1.java",
                    "code1_content": "package googlejam3.p211;\nimport java.io.*;\nimport java.util.*;\n\npublic class P1 {\n\n\tpublic static void main(String[] args) throws Exception {\n\t\tScanner scan = new Scanner(new File(\"in.txt\"));\n\t\tPrintWriter writer = new PrintWriter(\"out.txt\");\n\n\t\tint cases = scan.nextInt();\n\n\t\tfor (int i = 0; i < cases; i++) {\n\t\t\tint ans = 0;\n\n\t\t\tint r = scan.nextInt();\n\t\t\tint c = scan.nextInt();\n\t\t\tint w = scan.nextInt();\n\n\t\t\tans = (r - 1) * (c / w) + (c - 1) / w + w;\n\n\t\t\twriter.print(\"Case #\");\n\t\t\twriter.print(i+1);\n\t\t\twriter.print(\": \");\n\t\t\twriter.print(ans);\n\t\t\twriter.println();\n\t\t}\n\n\t\tscan.close();\n\t\twriter.close();\n\t}\n\n}\n",
                    "code2_path": "googlejam4_src/3/googlejam3.p250.A.java",
                    "code2_content": "package googlejam3.p250;\nimport java.io.*;\nimport java.util.*;\n\npublic class A{\n  public static void main (String[] args) throws Exception{\n    Scanner in = new Scanner (new FileReader(\"A-large.in\"));\n    PrintWriter out = new PrintWriter (\"A.out\");\n    int cases = in.nextInt();\n    for (int casenum = 1; casenum <= cases; casenum++){\n      \n      int r = in.nextInt();\n      int c = in.nextInt();\n      int w = in.nextInt();\n      int score = c/w*(r - 1);\n      score += (int)Math.ceil((double)c/(double)w);\n      score += w - 1;\n      \n      out.println (\"Case #\" + casenum + \": \" + score);\n    }\n    out.close();\n    in.close();\n  }\n}",
                    "label": 0
                },

            ],
            fileTree1: [],//左边存储文件结构
            fileTree2: [],//右边存储文件结构
            fileContent: [],//存储文件内容
            // codeText:[],
            activeName: 'first',
            activeNames: [],
            state: false,
            content1: '',
            tList: '',//传参存储
            intervalId: null,
            gpuList: [],
            gpuChoose: false, //gpu选择页面
            gpuProgress: [], // 各 GPU 的进度值
            clone_list: '',
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
                id: this.tList.id
            };
            post('/stop_test', JSON.stringify(jsonData), schoolInstance8)
        },
        //获取训练结果
        async getTestList() {
            try {
                const response = await get('/test/getAllTest');
                if (response.data.total != 0) {
                    for (const item of response.data.list) {
                        if (item.id == this.tList.id) {
                            if (item.result) {
                                const content = `${item.result
                                    .trim() // 去除首尾空白
                                    .split('\n') // 按行拆分
                                    .join('<br>')}`; // 用 <br> 连接每行
                                this.content1 = content;
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
        updateParameters() {
            console.log("this.tList", this.tList)
            console.log("this", this.tList["weight_decay"])
            this.parameters.forEach(paramGroup => {
                Object.keys(paramGroup).forEach(key => {
                    if (key.startsWith('value')) {
                        const keyIndex = key.replace('value', 'key');
                        const paramKey = paramGroup[keyIndex].replace(':', '');
                        if (paramKey && this.tList[paramKey] != undefined) {
                            paramGroup[key] = this.tList[paramKey];
                        }
                    }
                });
            });
            console.log("this.parameters", this.parameters)
        },
        startInterval() {
            console.log('2')
            this.intervalId = setInterval(async () => {
                const result = await this.getTestList();
            }, 2000);
        },
        getCellClassName({ row }) {
            return 'padding-right: 0 !important;'; 
        },
        handleClick(tab, event) {
            console.log(tab, event);
            if (tab.name == "second" && this.clone_list == '') {
                this.handleFileUpload();

            }
        },
        async handleFileUpload() {
            const jsonData1 = {
                "directoryPath": "googlejam4_src",
            }
            const res = String(this.tList.id)

            const jsonData2 = {
                id: this.tList.id
            }

            post('/clone_pairs', JSON.stringify(jsonData2), schoolInstance5)
                .then((clone_list) => {
                    this.clone_list = clone_list
                    const response = post('/directoryStructure', JSON.stringify(jsonData1), schoolInstance4)
                        .then((response) => {
                            this.addOpenProperty(response);
                            this.fileTree1 = [response]
                            this.fileTree2 = JSON.parse(JSON.stringify(this.fileTree1));
                        })
                        .catch((error) => {
                            console.error("请求失败:", error.message); 

                            this.fileTree1 = [
                                {
                                    "files": [
                                        {
                                            "files": [

                                                {
                                                    "isDirectory": false,
                                                    "name": "googlejam1.p019.Mushroom.java",
                                                    "path": "googlejam4_src/1/googlejam1.p019.Mushroom.java"
                                                },
                                                {
                                                    "isDirectory": false,
                                                    "name": "googlejam1.p021.mushroom.java",
                                                    "path": "googlejam4_src/1/googlejam1.p021.mushroom.java"
                                                },
                                                {
                                                    "isDirectory": false,
                                                    "name": "googlejam1.p022.A.java",
                                                    "path": "googlejam4_src/1/googlejam1.p022.A.java",
                                                    "isClone": true,
                                                    "content": "package googlejam1.p022;\nimport java.io.BufferedReader;\nimport java.io.InputStreamReader;\nimport java.util.StringTokenizer;\n\npublic class A {\n\n\tpublic static void main(String[] args) throws Exception {\n\t\tBufferedReader bf = new BufferedReader(new InputStreamReader(System.in));\n\n\t\tint ntests = Integer.parseInt(bf.readLine());\n\t\tfor (int tc = 1; tc <= ntests; tc++) {\n\t\t\tint n = Integer.parseInt(bf.readLine());\n\t\t\tint[] arr = new int[n];\n\t\t\tStringTokenizer st = new StringTokenizer(bf.readLine());\n\t\t\tfor (int i = 0; i < n; i++) {\n\t\t\t\tarr[i] = Integer.parseInt(st.nextToken());\n\t\t\t}\n\n\t\t\tint firstMin = 0;\n\t\t\tint maxDiff = 0;\n\t\t\tfor (int i = 1; i < n; i++) {\n\t\t\t\tint diff = -arr[i] + arr[i - 1];\n\t\t\t\tif (diff > 0) {\n\t\t\t\t\tfirstMin += diff;\n\t\t\t\t}\n\n\t\t\t\tmaxDiff = Math.max(maxDiff, diff);\n\t\t\t}\n\n\t\t\tint secondMin = 0;\n\t\t\tfor (int i = 1; i < n; i++) {\n\t\t\t\tsecondMin += Math.min(maxDiff, arr[i - 1]);\n\t\t\t}\n\n\t\t\tSystem.out.println(\"Case #\" + tc + \": \" + firstMin + \" \" + secondMin);\n\t\t}\n\t}\n}\n",
                                                    "clone_content": "package googlejam1.p041;\nimport java.util.ArrayList;\nimport java.util.HashSet;\nimport java.util.PriorityQueue;\nimport java.util.Scanner;\n\n/**\n * FileName: Main.java\n * @Description:\n *\n * @author Xunhu(Tiger) Sun\n *         email: sunx2013@my.fit.edu\n * @date Apr 10, 2015 8:05:22 PM\n */\npublic class Main {\n    final Scanner sc = new Scanner(System.in);\n\n    int n;\n    int[] a;\n\n    public static void main (String[] args) {\n        Main r = new Main();\n\t\tfinal int cn = r.sc.nextInt();\n\t\tr.sc.nextLine();\n\t\tfor (int ci = 1; ci <= cn; ci++) {\n\t\t    r.n = r.sc.nextInt();\n\t\t\tr.sc.nextLine();\n\t\t\tr.a = new int[r.n];\n\t\t\tfor(int i = 0; i < r.n; i++){\n\t\t\t    r.a[i] = r.sc.nextInt();\n\t\t\t}\n\t\t\tr.sc.nextLine();\n\t\t    System.out.printf(\"Case #%d: \", ci);\n\t\t    int sumrd =0;\n\t\t\tint maxrd = 0;\n\t\t\tfor(int i = 1; i < r.n; i++){\n\t\t\t    if(r.a[i] < r.a[i-1]){\n\t\t\t        int rd = r.a[i-1]-r.a[i];\n\t\t\t        sumrd+=rd;\n\t\t\t        if(maxrd< rd){\n\t\t\t            maxrd=rd;\n\t\t\t        }\n\t\t\t    }\n\t\t\t}\n\t\t\tint sum2 = 0;\n\t\t\tfor(int i = 0; i < r.n-1; i++){\n\t\t\t    int p = r.a[i];\n\t\t\t    if(p < maxrd){\n\t\t\t        sum2+=p;\n\t\t\t    } else{\n\t\t\t        sum2+=maxrd;\n\t\t\t    }\n\t\t\t}\n\t\t\tSystem.out.println(sumrd+\" \"+sum2);\n\t\t}\n    }\n}\n",
                                                    "clone_pth": "googlejam4_src/1/googlejam1.p041.A.java"
                                                },

                                                {
                                                    "isDirectory": false,
                                                    "name": "googlejam1.p041.A.java",
                                                    "path": "googlejam4_src/1/googlejam1.p041.A.java",
                                                    "isClone": true,
                                                    "content": "package googlejam1.p041;\nimport java.io.BufferedWriter;\nimport java.io.File;\nimport java.io.FileWriter;\nimport java.io.IOException;\nimport java.util.Scanner;\n\npublic class A {\n\n  static final String FILENAME = \"A-large\";\n  static final String IN = FILENAME + \".in\";\n  static final String OUT = FILENAME + \".out\";\n  Scanner sc;\n  BufferedWriter out;\n\n  public static void main(String args[]) throws Exception {\n    A r = new A();\n\tr.sc = new Scanner(new File(A.IN));\n\tr.out = new BufferedWriter(new FileWriter(A.OUT));\n\tint t = Integer.parseInt(r.sc.nextLine());\n\tfor (int i = 1; i <= t; i++) {\n\t  r.out.write(\"Case #\" + i + \": \");\n\t  int n = r.sc.nextInt();\n\tint[] m = new int[n];\n\tfor(int i1 = 0; i1 < n; i1++) {\n\t  m[i1] = r.sc.nextInt();\n\t}\n\tint sum1 = 0;\n\tint cur = m[0];\n\tfor(int i4 = 1; i4 < n; i4++) {\n\t  if(m[i4] < cur) {\n\t    sum1 += cur - m[i4];\n\t  }\n\t  cur = m[i4];\n\t}\n\tr.out.write(Integer.toString(sum1) + \" \");\n\t\n\tint max = 0;\n\tfor(int i2 = 1; i2 < n; i2++) {\n\t  if(max < m[i2 - 1] - m[i2]) {\n\t    max = m[i2 - 1] - m[i2];\n\t  }\n\t}\n\tint sum2 = 0;\n\tfor(int i3 = 0; i3 < n - 1; i3++) {\n\t  sum2 += Math.min(max, m[i3]);\n\t}\n\tr.out.write(Integer.toString(sum2));\n\tr.out.newLine();\n\tr.out.flush();\n\t}\n\tr.sc.close();\n\tr.out.close();\n  }\n}\n",
                                                    "clone_content": "package googlejam1.p022;\nimport java.io.BufferedReader;\nimport java.io.InputStreamReader;\nimport java.util.StringTokenizer;\n\npublic class A {\n\n\tpublic static void main(String[] args) throws Exception {\n\t\tBufferedReader bf = new BufferedReader(new InputStreamReader(System.in));\n\n\t\tint ntests = Integer.parseInt(bf.readLine());\n\t\tfor (int tc = 1; tc <= ntests; tc++) {\n\t\t\tint n = Integer.parseInt(bf.readLine());\n\t\t\tint[] arr = new int[n];\n\t\t\tStringTokenizer st = new StringTokenizer(bf.readLine());\n\t\t\tfor (int i = 0; i < n; i++) {\n\t\t\t\tarr[i] = Integer.parseInt(st.nextToken());\n\t\t\t}\n\n\t\t\tint firstMin = 0;\n\t\t\tint maxDiff = 0;\n\t\t\tfor (int i = 1; i < n; i++) {\n\t\t\t\tint diff = -arr[i] + arr[i - 1];\n\t\t\t\tif (diff > 0) {\n\t\t\t\t\tfirstMin += diff;\n\t\t\t\t}\n\n\t\t\t\tmaxDiff = Math.max(maxDiff, diff);\n\t\t\t}\n\n\t\t\tint secondMin = 0;\n\t\t\tfor (int i = 1; i < n; i++) {\n\t\t\t\tsecondMin += Math.min(maxDiff, arr[i - 1]);\n\t\t\t}\n\n\t\t\tSystem.out.println(\"Case #\" + tc + \": \" + firstMin + \" \" + secondMin);\n\t\t}\n\t}\n}\n",
                                                    "clone_pth": "googlejam4_src/1/googlejam1.p022.A.java"
                                                },
                                                {
                                                    "isDirectory": false,
                                                    "name": "googlejam1.p042.mushroom.java",
                                                    "path": "googlejam4_src/1/googlejam1.p042.mushroom.java"
                                                },
                                                {
                                                    "isDirectory": false,
                                                    "name": "googlejam1.p046.A.java",
                                                    "path": "googlejam4_src/1/googlejam1.p046.A.java"
                                                },

                                                {
                                                    "isDirectory": false,
                                                    "name": "googlejam1.p271.MushroomMonster.java",
                                                    "path": "googlejam4_src/1/googlejam1.p271.MushroomMonster.java"
                                                },
                                                {
                                                    "isDirectory": false,
                                                    "name": "googlejam1.p273.ProblemA.java",
                                                    "path": "googlejam4_src/1/googlejam1.p273.ProblemA.java"
                                                },

                                                {
                                                    "isDirectory": false,
                                                    "name": "googlejam1.p846.mush.java",
                                                    "path": "googlejam4_src/1/googlejam1.p846.mush.java"
                                                },
                                                {
                                                    "isDirectory": false,
                                                    "name": "googlejam1.p849.A.java",
                                                    "path": "googlejam4_src/1/googlejam1.p849.A.java"
                                                }
                                            ],
                                            "isDirectory": true,
                                            "name": "1",
                                            "isOpen": false
                                        },
                                        {
                                            "files": [
                                                {
                                                    "isDirectory": false,
                                                    "name": "googlejam2.p003.A.java",
                                                    "path": "googlejam4_src/2/googlejam2.p003.A.java"
                                                },
                                                {
                                                    "isDirectory": false,
                                                    "name": "googlejam2.p008.A.java",
                                                    "path": "googlejam4_src/2/googlejam2.p008.A.java"
                                                },

                                                {
                                                    "isDirectory": false,
                                                    "name": "googlejam2.p105.A.java",
                                                    "path": "googlejam4_src/2/googlejam2.p105.A.java",
                                                    "isClone": true,
                                                    "content": "package googlejam2.p105;\nimport java.util.Scanner;\n\n\npublic class A {\n\n\tpublic static void main(String[] args) {\n\n\t\tScanner k = new Scanner(System.in);\n\t\tint T = k.nextInt();\n\n\t\tfor(int i = 0; i < T; i++)\n\t\t{\n\t\t\tlong input = k.nextLong();\n\t\t\tlong output = 0;\n\n\t\t\twhile(input != 0)\n\t\t\t{\n\t\t\t\t//System.out.println(input);\n\t\t\t\tif(input <= 20)\n\t\t\t\t{\n\t\t\t\t\toutput += input;\n\t\t\t\t\tbreak;\n\t\t\t\t}\n\t\t\t\tlong flipThis = input;\n\t\t\t\tlong reverse = 0;\n\t\t\t\twhile( flipThis != 0 )\n\t\t\t\t{\n\t\t\t\t\treverse = reverse * 10;\n\t\t\t\t\treverse = reverse + flipThis%10;\n\t\t\t\t\tflipThis = flipThis/10;\n\t\t\t\t}\n\t\t\t\tlong flipped = reverse;\n\t\t\t\tif(flipped < input && input % Math.pow(10, (int)(Math.log10(input)+1)/2) == 1)\n\t\t\t\t{\n\t\t\t\t\tinput = flipped;\n\t\t\t\t}\n\t\t\t\telse\n\t\t\t\t{\n\t\t\t\t\tinput--;\n\t\t\t\t}\n\t\t\t\toutput++;\n\t\t\t}\n\t\t\tSystem.out.println(\"Case #\"+(i+1)+\": \" + output);\n\t\t}\n\t}\n}",
                                                    "clone_content": "package googlejam2.p221;\n\n\nimport java.io.BufferedReader;\nimport java.io.FileNotFoundException;\nimport java.io.FileReader;\nimport java.io.IOException;\nimport java.io.PrintWriter;\n\npublic class Counter {\n\n\tpublic static void main(String[] args) {\n\t\tString input = \"/Users/bhavesh/Downloads/A-small-attempt1.in\";\n\t\tString output = \"/Users/bhavesh/Downloads/output\";\n\n\t\ttry {\n\t\t\tint a[]= new int[1000001];\n\t\t\tint n;\n\t\t    for(int i=0;i<=1000000;i++){\n\t\t    \ta[i]=-1;\n\t\t    }\n\n\t\t    for(int i = 1; i<=1000000;i++)\n\t\t    {\n\t\t        if(i<=10)\n\t\t        {\n\t\t            a[i]=i;\n\t\t            continue;\n\t\t        }\n\t\t\t\tint n1 = i;\n\t\t\t\tint reverse = 0;\n\t\t\t\twhile (n1 != 0) {\n\t\t\t\t\treverse = reverse * 10;\n\t\t\t\t\treverse = reverse + n1 % 10;\n\t\t\t\t\tn1 = n1 / 10;\n\t\t\t\t}\n\t\t        int ri = reverse;\n\t\t        if(ri < i && i%10!=0)\n\t\t            a[i]= (a[i-1]+1< a[ri] + 1)?a[i-1]+1:a[ri] + 1;\n\t\t        else\n\t\t            a[i]= a[i-1]+1;\n\t\t    }\n\t\t\tBufferedReader br = new BufferedReader(new FileReader(input));\n\t\t\tPrintWriter pw = new PrintWriter(output);\n\n\t\t\tint t = Integer.parseInt(br.readLine().trim());\n\n\t\t\tint i = 1;\n\t\t\twhile (i <= t) {\n\t\t\t\tint num = Integer.parseInt(br.readLine());\n\n\t\t\t\tString op = \"Case #\" + i + \": \" +a[num];\n\t\t\tSystem.out.println(op);\n\t\t\t\tpw.print(op);\n\t\t\t\tpw.println();\n\t\t\t\ti++;\n\t\t\t}\n\n\t\t\tpw.flush();\n\t\t\tpw.close();\n\t\t\tbr.close();\n\t\t} catch (FileNotFoundException e) {\n\t\t\te.printStackTrace();\n\t\t} catch (IOException e) {\n\t\t\t// TODO Auto-generated catch block\n\t\t\te.printStackTrace();\n\t\t}\n\n\t}\n\n}",
                                                    "clone_pth": "googlejam4_src/2/googlejam2.p221.CounterCulture.java"
                                                },
                                                {
                                                    "isDirectory": false,
                                                    "name": "googlejam2.p108.a.java",
                                                    "path": "googlejam4_src/2/googlejam2.p108.a.java"
                                                },
                                                {
                                                    "isDirectory": false,
                                                    "name": "googlejam2.p114.Main.java",
                                                    "path": "googlejam4_src/2/googlejam2.p114.Main.java"
                                                },
                                                {
                                                    "isDirectory": false,
                                                    "name": "googlejam2.p116.countSmall.java",
                                                    "path": "googlejam4_src/2/googlejam2.p116.countSmall.java"
                                                },
                                                {
                                                    "isDirectory": false,
                                                    "name": "googlejam2.p121.A.java",
                                                    "path": "googlejam4_src/2/googlejam2.p121.A.java"
                                                },
                                                {
                                                    "isDirectory": false,
                                                    "name": "googlejam2.p127.Codejam.java",
                                                    "path": "googlejam4_src/2/googlejam2.p127.Codejam.java"
                                                },

                                                {
                                                    "isDirectory": false,
                                                    "name": "googlejam2.p221.CounterCulture.java",
                                                    "path": "googlejam4_src/2/googlejam2.p221.CounterCulture.java",
                                                    "isClone": true,
                                                    "content": "package googlejam2.p221;\nimport java.io.BufferedReader;\nimport java.io.FileReader;\nimport java.io.IOException;\nimport java.io.PrintWriter;\n\n\npublic class CounterCulture {\n\n\t/**\n\t * @param args\n\t */\n\tpublic static void main(String[] args) throws IOException {\n\t\tCounterCulture instance = new CounterCulture();\n\t\tBufferedReader br = new BufferedReader(new FileReader(\"in.txt\"));\n\t\tPrintWriter writer = new PrintWriter(\"out.txt\", \"UTF-8\");\n\t\t\n\t\t\n\t\tint MAX = 1000001;\n\t\t\n\t\tint[] res = new int[MAX];\n\t\t\n\t\tres[1] = 1;\n\t\t\n\t\tfor(int i=2;i<MAX;++i) {\n\t\t\tint r = Integer.parseInt(new StringBuffer(new Integer(i).toString()).reverse().toString());\n\t\t\tint r_r = Integer.parseInt(new StringBuffer(new Integer(r).toString()).reverse().toString());\n\t\t\tif(r<i && i == r_r) {\n\t\t\t\tres[i] = Math.min(res[i-1], res[r])+1;\n\t\t\t}\n\t\t\telse\n\t\t\t\tres[i] = res[i-1]+1;\n\t\t}\n\t\t\n\t\t\n\t\t\n\t\ttry {\n\t\t    String line = br.readLine();\n\t\t    \n\t\t    int t = Integer.parseInt(line);\n\t\t    \n\t\t    for(int i=1;i<=t;++i) {\n\t\t    \tint n = Integer.parseInt(br.readLine());\n\t\t    \t\n\t\t    \twriter.print(\"Case #\" + new Integer(i).toString() + \": \" + res[n]);\n\t\t    \t\n\t\t    \tif(i<t)\n\t\t    \t\twriter.println();\n\t\t    }\n\t\t} finally {\n\t\t    br.close();\n\t\t    writer.close();\n\t\t}\n\t}\n\n}\n",
                                                    "clone_content": "package googlejam2.p105;\nimport java.util.Scanner;\n\n\npublic class A {\n\n\tpublic static void main(String[] args) {\n\n\t\tScanner k = new Scanner(System.in);\n\t\tint T = k.nextInt();\n\n\t\tfor(int i = 0; i < T; i++)\n\t\t{\n\t\t\tlong input = k.nextLong();\n\t\t\tlong output = 0;\n\n\t\t\twhile(input != 0)\n\t\t\t{\n\t\t\t\t//System.out.println(input);\n\t\t\t\tif(input <= 20)\n\t\t\t\t{\n\t\t\t\t\toutput += input;\n\t\t\t\t\tbreak;\n\t\t\t\t}\n\t\t\t\tlong flipThis = input;\n\t\t\t\tlong reverse = 0;\n\t\t\t\twhile( flipThis != 0 )\n\t\t\t\t{\n\t\t\t\t\treverse = reverse * 10;\n\t\t\t\t\treverse = reverse + flipThis%10;\n\t\t\t\t\tflipThis = flipThis/10;\n\t\t\t\t}\n\t\t\t\tlong flipped = reverse;\n\t\t\t\tif(flipped < input && input % Math.pow(10, (int)(Math.log10(input)+1)/2) == 1)\n\t\t\t\t{\n\t\t\t\t\tinput = flipped;\n\t\t\t\t}\n\t\t\t\telse\n\t\t\t\t{\n\t\t\t\t\tinput--;\n\t\t\t\t}\n\t\t\t\toutput++;\n\t\t\t}\n\t\t\tSystem.out.println(\"Case #\"+(i+1)+\": \" + output);\n\t\t}\n\t}\n}",
                                                    "clone_pth": "googlejam4_src/2/googlejam2.p105.A.java"
                                                },

                                            ],
                                            "isDirectory": true,
                                            "name": "2",
                                            "isOpen": false
                                        },

                                    ],
                                    "isDirectory": true,
                                    "name": "googlejam4_src",
                                    "isOpen": false
                                }
                            ];
                            console.log('res', res)
                            this.fileTree2 = JSON.parse(JSON.stringify(this.fileTree1));
                        });

                })
                .catch((error) => {
                    console.error("请求失败:", error.message);

                    this.fileTree1 = [
                        {
                            "files": [
                                {
                                    "files": [

                                        {
                                            "isDirectory": false,
                                            "name": "googlejam1.p019.Mushroom.java",
                                            "path": "googlejam4_src/1/googlejam1.p019.Mushroom.java"
                                        },
                                        {
                                            "isDirectory": false,
                                            "name": "googlejam1.p021.mushroom.java",
                                            "path": "googlejam4_src/1/googlejam1.p021.mushroom.java"
                                        },
                                        {
                                            "isDirectory": false,
                                            "name": "googlejam1.p022.A.java",
                                            "path": "googlejam4_src/1/googlejam1.p022.A.java",
                                            "isClone": true,
                                            "content": "package googlejam1.p022;\nimport java.io.BufferedReader;\nimport java.io.InputStreamReader;\nimport java.util.StringTokenizer;\n\npublic class A {\n\n\tpublic static void main(String[] args) throws Exception {\n\t\tBufferedReader bf = new BufferedReader(new InputStreamReader(System.in));\n\n\t\tint ntests = Integer.parseInt(bf.readLine());\n\t\tfor (int tc = 1; tc <= ntests; tc++) {\n\t\t\tint n = Integer.parseInt(bf.readLine());\n\t\t\tint[] arr = new int[n];\n\t\t\tStringTokenizer st = new StringTokenizer(bf.readLine());\n\t\t\tfor (int i = 0; i < n; i++) {\n\t\t\t\tarr[i] = Integer.parseInt(st.nextToken());\n\t\t\t}\n\n\t\t\tint firstMin = 0;\n\t\t\tint maxDiff = 0;\n\t\t\tfor (int i = 1; i < n; i++) {\n\t\t\t\tint diff = -arr[i] + arr[i - 1];\n\t\t\t\tif (diff > 0) {\n\t\t\t\t\tfirstMin += diff;\n\t\t\t\t}\n\n\t\t\t\tmaxDiff = Math.max(maxDiff, diff);\n\t\t\t}\n\n\t\t\tint secondMin = 0;\n\t\t\tfor (int i = 1; i < n; i++) {\n\t\t\t\tsecondMin += Math.min(maxDiff, arr[i - 1]);\n\t\t\t}\n\n\t\t\tSystem.out.println(\"Case #\" + tc + \": \" + firstMin + \" \" + secondMin);\n\t\t}\n\t}\n}\n",
                                            "clone_content": "package googlejam1.p041;\nimport java.util.ArrayList;\nimport java.util.HashSet;\nimport java.util.PriorityQueue;\nimport java.util.Scanner;\n\n/**\n * FileName: Main.java\n * @Description:\n *\n * @author Xunhu(Tiger) Sun\n *         email: sunx2013@my.fit.edu\n * @date Apr 10, 2015 8:05:22 PM\n */\npublic class Main {\n    final Scanner sc = new Scanner(System.in);\n\n    int n;\n    int[] a;\n\n    public static void main (String[] args) {\n        Main r = new Main();\n\t\tfinal int cn = r.sc.nextInt();\n\t\tr.sc.nextLine();\n\t\tfor (int ci = 1; ci <= cn; ci++) {\n\t\t    r.n = r.sc.nextInt();\n\t\t\tr.sc.nextLine();\n\t\t\tr.a = new int[r.n];\n\t\t\tfor(int i = 0; i < r.n; i++){\n\t\t\t    r.a[i] = r.sc.nextInt();\n\t\t\t}\n\t\t\tr.sc.nextLine();\n\t\t    System.out.printf(\"Case #%d: \", ci);\n\t\t    int sumrd =0;\n\t\t\tint maxrd = 0;\n\t\t\tfor(int i = 1; i < r.n; i++){\n\t\t\t    if(r.a[i] < r.a[i-1]){\n\t\t\t        int rd = r.a[i-1]-r.a[i];\n\t\t\t        sumrd+=rd;\n\t\t\t        if(maxrd< rd){\n\t\t\t            maxrd=rd;\n\t\t\t        }\n\t\t\t    }\n\t\t\t}\n\t\t\tint sum2 = 0;\n\t\t\tfor(int i = 0; i < r.n-1; i++){\n\t\t\t    int p = r.a[i];\n\t\t\t    if(p < maxrd){\n\t\t\t        sum2+=p;\n\t\t\t    } else{\n\t\t\t        sum2+=maxrd;\n\t\t\t    }\n\t\t\t}\n\t\t\tSystem.out.println(sumrd+\" \"+sum2);\n\t\t}\n    }\n}\n",
                                            "clone_pth": "googlejam4_src/1/googlejam1.p041.A.java"
                                        },

                                        {
                                            "isDirectory": false,
                                            "name": "googlejam1.p041.A.java",
                                            "path": "googlejam4_src/1/googlejam1.p041.A.java",
                                            "isClone": true,
                                            "content": "package googlejam1.p041;\nimport java.io.BufferedWriter;\nimport java.io.File;\nimport java.io.FileWriter;\nimport java.io.IOException;\nimport java.util.Scanner;\n\npublic class A {\n\n  static final String FILENAME = \"A-large\";\n  static final String IN = FILENAME + \".in\";\n  static final String OUT = FILENAME + \".out\";\n  Scanner sc;\n  BufferedWriter out;\n\n  public static void main(String args[]) throws Exception {\n    A r = new A();\n\tr.sc = new Scanner(new File(A.IN));\n\tr.out = new BufferedWriter(new FileWriter(A.OUT));\n\tint t = Integer.parseInt(r.sc.nextLine());\n\tfor (int i = 1; i <= t; i++) {\n\t  r.out.write(\"Case #\" + i + \": \");\n\t  int n = r.sc.nextInt();\n\tint[] m = new int[n];\n\tfor(int i1 = 0; i1 < n; i1++) {\n\t  m[i1] = r.sc.nextInt();\n\t}\n\tint sum1 = 0;\n\tint cur = m[0];\n\tfor(int i4 = 1; i4 < n; i4++) {\n\t  if(m[i4] < cur) {\n\t    sum1 += cur - m[i4];\n\t  }\n\t  cur = m[i4];\n\t}\n\tr.out.write(Integer.toString(sum1) + \" \");\n\t\n\tint max = 0;\n\tfor(int i2 = 1; i2 < n; i2++) {\n\t  if(max < m[i2 - 1] - m[i2]) {\n\t    max = m[i2 - 1] - m[i2];\n\t  }\n\t}\n\tint sum2 = 0;\n\tfor(int i3 = 0; i3 < n - 1; i3++) {\n\t  sum2 += Math.min(max, m[i3]);\n\t}\n\tr.out.write(Integer.toString(sum2));\n\tr.out.newLine();\n\tr.out.flush();\n\t}\n\tr.sc.close();\n\tr.out.close();\n  }\n}\n",
                                            "clone_content": "package googlejam1.p022;\nimport java.io.BufferedReader;\nimport java.io.InputStreamReader;\nimport java.util.StringTokenizer;\n\npublic class A {\n\n\tpublic static void main(String[] args) throws Exception {\n\t\tBufferedReader bf = new BufferedReader(new InputStreamReader(System.in));\n\n\t\tint ntests = Integer.parseInt(bf.readLine());\n\t\tfor (int tc = 1; tc <= ntests; tc++) {\n\t\t\tint n = Integer.parseInt(bf.readLine());\n\t\t\tint[] arr = new int[n];\n\t\t\tStringTokenizer st = new StringTokenizer(bf.readLine());\n\t\t\tfor (int i = 0; i < n; i++) {\n\t\t\t\tarr[i] = Integer.parseInt(st.nextToken());\n\t\t\t}\n\n\t\t\tint firstMin = 0;\n\t\t\tint maxDiff = 0;\n\t\t\tfor (int i = 1; i < n; i++) {\n\t\t\t\tint diff = -arr[i] + arr[i - 1];\n\t\t\t\tif (diff > 0) {\n\t\t\t\t\tfirstMin += diff;\n\t\t\t\t}\n\n\t\t\t\tmaxDiff = Math.max(maxDiff, diff);\n\t\t\t}\n\n\t\t\tint secondMin = 0;\n\t\t\tfor (int i = 1; i < n; i++) {\n\t\t\t\tsecondMin += Math.min(maxDiff, arr[i - 1]);\n\t\t\t}\n\n\t\t\tSystem.out.println(\"Case #\" + tc + \": \" + firstMin + \" \" + secondMin);\n\t\t}\n\t}\n}\n",
                                            "clone_pth": "googlejam4_src/1/googlejam1.p022.A.java"
                                        },
                                        {
                                            "isDirectory": false,
                                            "name": "googlejam1.p042.mushroom.java",
                                            "path": "googlejam4_src/1/googlejam1.p042.mushroom.java"
                                        },
                                        {
                                            "isDirectory": false,
                                            "name": "googlejam1.p046.A.java",
                                            "path": "googlejam4_src/1/googlejam1.p046.A.java"
                                        },

                                        {
                                            "isDirectory": false,
                                            "name": "googlejam1.p271.MushroomMonster.java",
                                            "path": "googlejam4_src/1/googlejam1.p271.MushroomMonster.java"
                                        },
                                        {
                                            "isDirectory": false,
                                            "name": "googlejam1.p273.ProblemA.java",
                                            "path": "googlejam4_src/1/googlejam1.p273.ProblemA.java"
                                        },

                                        {
                                            "isDirectory": false,
                                            "name": "googlejam1.p846.mush.java",
                                            "path": "googlejam4_src/1/googlejam1.p846.mush.java"
                                        },
                                        {
                                            "isDirectory": false,
                                            "name": "googlejam1.p849.A.java",
                                            "path": "googlejam4_src/1/googlejam1.p849.A.java"
                                        }
                                    ],
                                    "isDirectory": true,
                                    "name": "1",
                                    "isOpen": false
                                },
                                {
                                    "files": [
                                        {
                                            "isDirectory": false,
                                            "name": "googlejam2.p003.A.java",
                                            "path": "googlejam4_src/2/googlejam2.p003.A.java"
                                        },
                                        {
                                            "isDirectory": false,
                                            "name": "googlejam2.p008.A.java",
                                            "path": "googlejam4_src/2/googlejam2.p008.A.java"
                                        },

                                        {
                                            "isDirectory": false,
                                            "name": "googlejam2.p105.A.java",
                                            "path": "googlejam4_src/2/googlejam2.p105.A.java",
                                            "isClone": true,
                                            "content": "package googlejam2.p105;\nimport java.util.Scanner;\n\n\npublic class A {\n\n\tpublic static void main(String[] args) {\n\n\t\tScanner k = new Scanner(System.in);\n\t\tint T = k.nextInt();\n\n\t\tfor(int i = 0; i < T; i++)\n\t\t{\n\t\t\tlong input = k.nextLong();\n\t\t\tlong output = 0;\n\n\t\t\twhile(input != 0)\n\t\t\t{\n\t\t\t\t//System.out.println(input);\n\t\t\t\tif(input <= 20)\n\t\t\t\t{\n\t\t\t\t\toutput += input;\n\t\t\t\t\tbreak;\n\t\t\t\t}\n\t\t\t\tlong flipThis = input;\n\t\t\t\tlong reverse = 0;\n\t\t\t\twhile( flipThis != 0 )\n\t\t\t\t{\n\t\t\t\t\treverse = reverse * 10;\n\t\t\t\t\treverse = reverse + flipThis%10;\n\t\t\t\t\tflipThis = flipThis/10;\n\t\t\t\t}\n\t\t\t\tlong flipped = reverse;\n\t\t\t\tif(flipped < input && input % Math.pow(10, (int)(Math.log10(input)+1)/2) == 1)\n\t\t\t\t{\n\t\t\t\t\tinput = flipped;\n\t\t\t\t}\n\t\t\t\telse\n\t\t\t\t{\n\t\t\t\t\tinput--;\n\t\t\t\t}\n\t\t\t\toutput++;\n\t\t\t}\n\t\t\tSystem.out.println(\"Case #\"+(i+1)+\": \" + output);\n\t\t}\n\t}\n}",
                                            "clone_content": "package googlejam2.p221;\n\n\nimport java.io.BufferedReader;\nimport java.io.FileNotFoundException;\nimport java.io.FileReader;\nimport java.io.IOException;\nimport java.io.PrintWriter;\n\npublic class Counter {\n\n\tpublic static void main(String[] args) {\n\t\tString input = \"/Users/bhavesh/Downloads/A-small-attempt1.in\";\n\t\tString output = \"/Users/bhavesh/Downloads/output\";\n\n\t\ttry {\n\t\t\tint a[]= new int[1000001];\n\t\t\tint n;\n\t\t    for(int i=0;i<=1000000;i++){\n\t\t    \ta[i]=-1;\n\t\t    }\n\n\t\t    for(int i = 1; i<=1000000;i++)\n\t\t    {\n\t\t        if(i<=10)\n\t\t        {\n\t\t            a[i]=i;\n\t\t            continue;\n\t\t        }\n\t\t\t\tint n1 = i;\n\t\t\t\tint reverse = 0;\n\t\t\t\twhile (n1 != 0) {\n\t\t\t\t\treverse = reverse * 10;\n\t\t\t\t\treverse = reverse + n1 % 10;\n\t\t\t\t\tn1 = n1 / 10;\n\t\t\t\t}\n\t\t        int ri = reverse;\n\t\t        if(ri < i && i%10!=0)\n\t\t            a[i]= (a[i-1]+1< a[ri] + 1)?a[i-1]+1:a[ri] + 1;\n\t\t        else\n\t\t            a[i]= a[i-1]+1;\n\t\t    }\n\t\t\tBufferedReader br = new BufferedReader(new FileReader(input));\n\t\t\tPrintWriter pw = new PrintWriter(output);\n\n\t\t\tint t = Integer.parseInt(br.readLine().trim());\n\n\t\t\tint i = 1;\n\t\t\twhile (i <= t) {\n\t\t\t\tint num = Integer.parseInt(br.readLine());\n\n\t\t\t\tString op = \"Case #\" + i + \": \" +a[num];\n\t\t\tSystem.out.println(op);\n\t\t\t\tpw.print(op);\n\t\t\t\tpw.println();\n\t\t\t\ti++;\n\t\t\t}\n\n\t\t\tpw.flush();\n\t\t\tpw.close();\n\t\t\tbr.close();\n\t\t} catch (FileNotFoundException e) {\n\t\t\te.printStackTrace();\n\t\t} catch (IOException e) {\n\t\t\t// TODO Auto-generated catch block\n\t\t\te.printStackTrace();\n\t\t}\n\n\t}\n\n}",
                                            "clone_pth": "googlejam4_src/2/googlejam2.p221.CounterCulture.java"
                                        },
                                        {
                                            "isDirectory": false,
                                            "name": "googlejam2.p108.a.java",
                                            "path": "googlejam4_src/2/googlejam2.p108.a.java"
                                        },
                                        {
                                            "isDirectory": false,
                                            "name": "googlejam2.p114.Main.java",
                                            "path": "googlejam4_src/2/googlejam2.p114.Main.java"
                                        },
                                        {
                                            "isDirectory": false,
                                            "name": "googlejam2.p116.countSmall.java",
                                            "path": "googlejam4_src/2/googlejam2.p116.countSmall.java"
                                        },
                                        {
                                            "isDirectory": false,
                                            "name": "googlejam2.p121.A.java",
                                            "path": "googlejam4_src/2/googlejam2.p121.A.java"
                                        },
                                        {
                                            "isDirectory": false,
                                            "name": "googlejam2.p127.Codejam.java",
                                            "path": "googlejam4_src/2/googlejam2.p127.Codejam.java"
                                        },

                                        {
                                            "isDirectory": false,
                                            "name": "googlejam2.p221.CounterCulture.java",
                                            "path": "googlejam4_src/2/googlejam2.p221.CounterCulture.java",
                                            "isClone": true,
                                            "content": "package googlejam2.p221;\nimport java.io.BufferedReader;\nimport java.io.FileReader;\nimport java.io.IOException;\nimport java.io.PrintWriter;\n\n\npublic class CounterCulture {\n\n\t/**\n\t * @param args\n\t */\n\tpublic static void main(String[] args) throws IOException {\n\t\tCounterCulture instance = new CounterCulture();\n\t\tBufferedReader br = new BufferedReader(new FileReader(\"in.txt\"));\n\t\tPrintWriter writer = new PrintWriter(\"out.txt\", \"UTF-8\");\n\t\t\n\t\t\n\t\tint MAX = 1000001;\n\t\t\n\t\tint[] res = new int[MAX];\n\t\t\n\t\tres[1] = 1;\n\t\t\n\t\tfor(int i=2;i<MAX;++i) {\n\t\t\tint r = Integer.parseInt(new StringBuffer(new Integer(i).toString()).reverse().toString());\n\t\t\tint r_r = Integer.parseInt(new StringBuffer(new Integer(r).toString()).reverse().toString());\n\t\t\tif(r<i && i == r_r) {\n\t\t\t\tres[i] = Math.min(res[i-1], res[r])+1;\n\t\t\t}\n\t\t\telse\n\t\t\t\tres[i] = res[i-1]+1;\n\t\t}\n\t\t\n\t\t\n\t\t\n\t\ttry {\n\t\t    String line = br.readLine();\n\t\t    \n\t\t    int t = Integer.parseInt(line);\n\t\t    \n\t\t    for(int i=1;i<=t;++i) {\n\t\t    \tint n = Integer.parseInt(br.readLine());\n\t\t    \t\n\t\t    \twriter.print(\"Case #\" + new Integer(i).toString() + \": \" + res[n]);\n\t\t    \t\n\t\t    \tif(i<t)\n\t\t    \t\twriter.println();\n\t\t    }\n\t\t} finally {\n\t\t    br.close();\n\t\t    writer.close();\n\t\t}\n\t}\n\n}\n",
                                            "clone_content": "package googlejam2.p105;\nimport java.util.Scanner;\n\n\npublic class A {\n\n\tpublic static void main(String[] args) {\n\n\t\tScanner k = new Scanner(System.in);\n\t\tint T = k.nextInt();\n\n\t\tfor(int i = 0; i < T; i++)\n\t\t{\n\t\t\tlong input = k.nextLong();\n\t\t\tlong output = 0;\n\n\t\t\twhile(input != 0)\n\t\t\t{\n\t\t\t\t//System.out.println(input);\n\t\t\t\tif(input <= 20)\n\t\t\t\t{\n\t\t\t\t\toutput += input;\n\t\t\t\t\tbreak;\n\t\t\t\t}\n\t\t\t\tlong flipThis = input;\n\t\t\t\tlong reverse = 0;\n\t\t\t\twhile( flipThis != 0 )\n\t\t\t\t{\n\t\t\t\t\treverse = reverse * 10;\n\t\t\t\t\treverse = reverse + flipThis%10;\n\t\t\t\t\tflipThis = flipThis/10;\n\t\t\t\t}\n\t\t\t\tlong flipped = reverse;\n\t\t\t\tif(flipped < input && input % Math.pow(10, (int)(Math.log10(input)+1)/2) == 1)\n\t\t\t\t{\n\t\t\t\t\tinput = flipped;\n\t\t\t\t}\n\t\t\t\telse\n\t\t\t\t{\n\t\t\t\t\tinput--;\n\t\t\t\t}\n\t\t\t\toutput++;\n\t\t\t}\n\t\t\tSystem.out.println(\"Case #\"+(i+1)+\": \" + output);\n\t\t}\n\t}\n}",
                                            "clone_pth": "googlejam4_src/2/googlejam2.p105.A.java"
                                        },

                                    ],
                                    "isDirectory": true,
                                    "name": "2",
                                    "isOpen": false
                                },

                            ],
                            "isDirectory": true,
                            "name": "googlejam4_src",
                            "isOpen": false
                        }
                    ];
                    console.log('res', res)
                    this.fileTree2 = JSON.parse(JSON.stringify(this.fileTree1));
                });

        },
        addOpenProperty(node) {

            if (node.isDirectory) {
                node.isOpen = false; 
            }
            if (Array.isArray(node.files) && node.files.length > 0) {
                node.files.forEach(childNode => this.addOpenProperty(childNode)); 
            } else {
                const match = this.clone_list.find(
                    item => {
                        if (item.code1_path == node.path && item.label == 1) {
                            node.isClone = true
                            node.content = item.code1_content
                            node.clone_content = item.code2_content
                            node.clone_pth = item.code2_path
                        } else if (item.code2_path == node.path && item.label == 1) {
                            node.isClone = true
                            node.content = item.code2_content
                            node.clone_content = item.code1_content
                            node.clone_pth = item.code1_path
                        }

                    }
                );
            }
        },
        insertFile(tree, pathSegments, file) {
            const name = pathSegments.shift(); // 取出第一个目录/文件名
            let node = tree.find(item => item.name === name);
            if (!node) {
                node = { name: name, files: [], isOpen: false };
                console.log("node", node)
                if (!node.name.startsWith('.')) {
                    tree.push(node);
                }

            }
            if (pathSegments.length === 0) {
                // 如果没有更多的子目录，读取文件内容
                const reader = new FileReader();
                let con
                reader.onload = (e) => {
                    console.log(" node.content", e)
                    this.fileContent[file.name] = e.target.result;
                    con = e.target.result
                };

                reader.readAsText(file);
                let isClone = false;

                this.textResult.some(item => {
                    if (item.code1_path == file.webkitRelativePath && item.label == 1) {
                        node.files.push({ name: file.name, path: file.webkitRelativePath, isClone: true, content: item.code1_content, clone_content: item.code2_content, clone_pth: item.code2_path });
                        isClone = true;
                        return true; 
                    } else if (item.code2_path == file.webkitRelativePath && item.label == 1) {
                        node.files.push({ name: file.name, path: file.webkitRelativePath, isClone: true, content: item.code2_content, clone_content: item.code1_content, clone_pth: item.code1_path });
                        isClone = true; 
                        return true; 
                    }
                });
                if (!isClone) {
                    node.files.push({ name: file.name, path: file.webkitRelativePath, isClone: false });
                }
            } else {
                // 继续递归处理子目录
                this.insertFile(node.files, pathSegments, file);
            }

        },
        async getGpuUsed() {
            try {
                const response = await schoolInstance2.get('/gpu_usage'); 
                console.log('gpu和内存使用率:', response);
                this.gpuList = Object.values(response); 
                this.gpuList.forEach((gpu, index) => {
                    this.gpuProgress[index] = parseFloat(gpu.memory_utilization_percent);
                })
            } catch (error) {
                console.error('请求错误:', error); // 捕获并输出请求错误
            }
        },
        //展示克隆代码和对应的克隆目录
        show(sub) {
            // 先关闭所有目录
            this.closeAllDirectories(this.fileTree2);
            console.log("sub", sub)
            let content = `源代码：<br>${sub.content
                .trim() // 去除首尾空白
                .split('\n') // 按行拆分
                .join('<br>')
                }`; // 用 <br> 连接每行
            let clone_content = `疑似克隆代码：<br>${sub.clone_content
                .trim() // 去除首尾空白
                .split('\n') // 按行拆分
                .join('<br>')
                }`;
            const container1 = document.querySelector('.center1');
            const container2 = document.querySelector('.center2');
            container1.innerHTML = content
            container2.innerHTML = clone_content
            // 展开右边的目录
            this.printPaths(this.fileTree2, sub.clone_pth)

        },
        //递归判断path是否相等
        printPaths(files, targetPath) {
            let found = false;
            files.forEach(file => {
                if (file.isDirectory) {
                    let foundInChild = this.printPaths(file.files, targetPath);
                    if (foundInChild) {
                        this.$set(file, 'isOpen', true); 
                        found = true; 
                    }
                } else {
                    if (file && targetPath == file.path) {
                        this.$set(file, 'isOpen', true);
                        console.log("找到目标文件：", file);
                        found = true; 
                    }
                }
            });
            return found; 
        },
        closeAllDirectories(files) {
            files.forEach(file => {
                if (file.isDirectory) {
                    this.$set(file, 'isOpen', false); 
                    this.closeAllDirectories(file.files);
                }
            });
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
        gpuSubmit() {
            this.$refs.tList.validate((valid) => {
                if (valid) {
                    put('/test/updateTest', this.tList)
                        .then(response => {
                            this.gpuChoose = false; 
                            const jsonData = JSON.stringify(this.tList); 
                            return post('/test', jsonData, schoolInstance1);
                        })

                    // 启动定时器获取数据
                    this.intervalId = setInterval(async () => {
                        const result = await this.getTestList();
                        get(`/test/getTestById/${this.tList.id}`)
                            .then(state => {
                                console.log('state', state.data.state);
                                if (state.data.state == 1) {
                                    clearInterval(this.intervalId);
                                    this.state = true
                                    //获取运行结果
                                    this.handleFileUpload();
                                }
                            })
                    }, 2000);

                }
            })
        },
        //获取测试结果接口
        async getResult() {
            try {
                const jsonData = JSON.stringify(this.tList.id);
                const response = await post('/clone_pairs', jsonData, schoolInstance4);
            } catch (error) {
                console.error('请求错误:', error); 
            }
        },
        scrollToBottom() {
            const container = document.querySelector('.a2');
            if (container) {
                container.scrollTop = container.scrollHeight;
            }
        },
    },

    created() {
        //获取父组件传递内容
        const tList = JSON.parse(this.$route.query.row);
        this.tList = tList
        if (tList.state == '1') {
            this.state = true
            this.handleFileUpload();
            this.progress = 100
        } else {
            this.state = false
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
@import"../../../../css/testDisplay.css";
</style>
