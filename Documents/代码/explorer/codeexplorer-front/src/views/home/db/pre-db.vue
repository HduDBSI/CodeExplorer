<template>
    <div class="on">

        <div>
            <el-row class="row">
                <p class="font">代码数据集信息表</p>
                <el-button class="button" type="primary" size="small" icon="el-icon-plus"
                    @click="MessageBox()">新增原始数据集</el-button>
            </el-row>
            <div class="table1">
                <el-table :data="tableData" style="width: 100%; height:100%" :header-cell-style="headerRowStyle" border>
                    <el-table-column prop="id" label="编号" min-width="80" align="left"></el-table-column>
                    <el-table-column prop="dataset" label="数据集名称" min-width="300" align="left"></el-table-column>
                    <el-table-column prop="remark" label="描述" min-width="300" align="left"></el-table-column>
                    <el-table-column prop="state" label="状态" min-width="130.5" align="left">
                        <template slot-scope="scope">
                            <span>
                                <img v-if="scope.row.result == '未预处理'" src="../../../assets/Oval@2x-3.png"
                                    style="width: 14px; height: 14px; margin-right: 3px;">
                                <img v-else-if="scope.row.result == '预处理完成'" src="../../../assets/Oval@2x-2.png"
                                    style="width: 14px; height: 14px; margin-right: 3px;">
                                {{ scope.row.result == '未预处理' ? '未处理' : '已处理' }}
                            </span>
                        </template>
                    </el-table-column>
                    <el-table-column prop="time" label="创建日期" min-width="200" align="left"></el-table-column>
                    <el-table-column fixed="right" label="操作" min-width="150" align="left">
                        <template v-slot="scope">
                            <el-button type="text" size="small" align="center" :style="{ color: '#1890FF' }"
                                @click="open(scope.row)">查看</el-button>
                            <el-button type="text" size="small" :style="{ color: '#FF4D4F' }"
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
        <!--查看界面-->
        <div>
            <el-dialog title="数据集内容" :visible.sync="openVisible" width="70%">
                <div class="bigDiv">
                    <!-- 运行结果内容 -->
                    <el-row type="flex" :gutter="4" justify="space-between" class="bigrow">
                        <el-col :span="9" class="bg-purple-left ">
                            <div v-if="fileTree.length" class="fileTree">
                                <h3>文件结构:</h3>
                                <ul class="fileStyle">
                                    <li v-for="(file, index) in fileTree" :key="index">
                                        <span @click="file.isOpen = !file.isOpen" style="cursor: pointer;">
                                            <span v-if="file.isDirectory" class="arrow">{{ file.isOpen ? ' ▼' : ' ►'
                                                }}</span>
                                            {{ file.name }}
                                        </span>
                                        <ul v-if="file.isDirectory && file.isOpen">
                                            <li v-for="(child, idx) in file.files" :key="idx">
                                                <span @click="child.isOpen = !child.isOpen" style="cursor: pointer;">
                                                    <span v-if="child.isDirectory" class="arrow">{{ child.isOpen ?
                                                        '▼'
                                                        :
                                                        '►' }}</span>
                                                    {{ child.name }}
                                                </span>
                                                <ul v-if="child.isDirectory && child.isOpen">
                                                    <li v-for="(subChild, subIdx) in child.files" :key="subIdx">
                                                        <!-- {{ subChild.name }} -->
                                                        <span style="cursor:pointer;
                                                        " @click="show(subChild)">{{
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
                        <el-col :span="15">
                            <div class="grid-content bg-purple ">
                                <pre v-highlight><code class="center1"></code></pre>
                            </div>
                        </el-col>
                    </el-row>
                </div>
            </el-dialog>
        </div>
        <el-dialog title="新增数据集" :visible.sync="editVisible" width="586px" height="431px" left class="custom-dialog">
            <el-upload ref="upload" :action="uploadUrl" class="upload-demo" :file-list="fileList" :auto-upload="false" :limit="1" :accept="'.zip'" :before-upload="beforeUpload" :on-change="handleChange" drag>
                <img :src="require('@/assets/上传按钮@2x.png')" class="update-icon" />
                <div class="el-upload__text" @click="triggerFileInput">将文件拖拽至此区域，或 <em> 点击选取文件</em>
                    <div class="el-upload__tip" slot="tip">仅支持zip格式，解压后文件需有一级目录</div>
                </div>

            </el-upload>
            <div class="button-container">
                <el-button size="small" style="font-size:14px;margin-left: 0px; " @click="editVisible = false">取消</el-button>
                <el-button size="small"  style="font-size:14px;margin-left: 16px;" type="primary" @click="submitUpload"
                    >上传到服务器</el-button>
            </div>
        </el-dialog>
    </div>
</template>

<script>
import { get, del, post } from '../../../api/request';
import { schoolInstance4, schoolInstance7 } from '../../../axios';
export default {
    data() {
        return {
            tableData: [
                // { id: 1, name: '第一个未预处理数据集', state: '未处理', time: '2024.08.14' },
                // { id: 2, name: '第一个已预处理数据集', state: '已处理', time: '2024.08.14' },
            ],
            openVisible: false,
            fileTree: [],
            editVisible: false,
            fileList: [],
            uploadUrl: '/upload',
            currentPage1:1
        }
    },
    mounted() {
        this.getDbList();
    },
    methods: {
        headerRowStyle() {
            return {
                backgroundColor: 'rgba(243, 249, 254, 0.6)',
                color: '#000',
                padding: '17px 16px 17px 0px',
                border: '0.3px solid rgba(240, 244, 248, 0.6)',

            }
        },
        async getDbList() {
            try {
                const response = await get('/dataset/getAllDatasets');
                console.log("response", response)
                this.tableData = response.data.list
            } catch (error) {
                console.log('请求模型表格数据失败:', error)
            }
        },
        del(row) {
            this.$confirm('是否删除当前数据?', '提示', {
                confirmButtonText: '确定',
                cancelButtonText: '取消',
                type: 'warning'
            }).then(async () => {
                try {
                    const response = await del(`/dataset/deleteDatasetById/${row.id}`)
                    this.tableData = this.tableData.filter(item => item.id !== row.id)

                    this.$message({
                        type: 'success',
                        message: '删除成功!'
                    });

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
        open(row) {
            this.openVisible = true

            if (row.state == 1) {
                const jsonData1 = {
                    "directoryPath": "googlejam4_src",
                }
                //获取目录结构
                const response = post('/directoryStructure', JSON.stringify(jsonData1), schoolInstance4)
                    .then((response) => {
                        this.addOpenProperty(response);
                        this.fileTree = [response]
                    }).catch((error) => {
                        console.error("请求失败:", error.message); 

                        this.fileTree = [
                            {
                                "files": [
                                    {
                                        "files": [

                                            {
                                                "isDirectory": false,
                                                "name": "googlejam1.p019.Mushroom.java",
                                                "path": "googlejam4_src/1/googlejam1.p019.Mushroom.java",
                                                "content": "package googlejam1.p019;\nimport java.io.BufferedReader;\nimport java.io.InputStreamReader;\nimport java.util.StringTokenizer;\n\npublic class A {\n\n\tpublic static void main(String[] args) throws Exception {\n\t\tBufferedReader bf = new BufferedReader(new InputStreamReader(System.in));\n\n\t\tint ntests = Integer.parseInt(bf.readLine());\n\t\tfor (int tc = 1; tc <= ntests; tc++) {\n\t\t\tint n = Integer.parseInt(bf.readLine());\n\t\t\tint[] arr = new int[n];\n\t\t\tStringTokenizer st = new StringTokenizer(bf.readLine());\n\t\t\tfor (int i = 0; i < n; i++) {\n\t\t\t\tarr[i] = Integer.parseInt(st.nextToken());\n\t\t\t}\n\n\t\t\tint firstMin = 0;\n\t\t\tint maxDiff = 0;\n\t\t\tfor (int i = 1; i < n; i++) {\n\t\t\t\tint diff = -arr[i] + arr[i - 1];\n\t\t\t\tif (diff > 0) {\n\t\t\t\t\tfirstMin += diff;\n\t\t\t\t}\n\n\t\t\t\tmaxDiff = Math.max(maxDiff, diff);\n\t\t\t}\n\n\t\t\tint secondMin = 0;\n\t\t\tfor (int i = 1; i < n; i++) {\n\t\t\t\tsecondMin += Math.min(maxDiff, arr[i - 1]);\n\t\t\t}\n\n\t\t\tSystem.out.println(\"Case #\" + tc + \": \" + firstMin + \" \" + secondMin);\n\t\t}\n\t}\n}\n",
                                            },
                                            {
                                                "isDirectory": false,
                                                "name": "googlejam1.p021.mushroom.java",
                                                "path": "googlejam4_src/1/googlejam1.p021.mushroom.java",
                                                "content": "package googlejam1.p021;\nimport java.io.BufferedReader;\nimport java.io.InputStreamReader;\nimport java.util.StringTokenizer;\n\npublic class A {\n\n\tpublic static void main(String[] args) throws Exception {\n\t\tBufferedReader bf = new BufferedReader(new InputStreamReader(System.in));\n\n\t\tint ntests = Integer.parseInt(bf.readLine());\n\t\tfor (int tc = 1; tc <= ntests; tc++) {\n\t\t\tint n = Integer.parseInt(bf.readLine());\n\t\t\tint[] arr = new int[n];\n\t\t\tStringTokenizer st = new StringTokenizer(bf.readLine());\n\t\t\tfor (int i = 0; i < n; i++) {\n\t\t\t\tarr[i] = Integer.parseInt(st.nextToken());\n\t\t\t}\n\n\t\t\tint firstMin = 0;\n\t\t\tint maxDiff = 0;\n\t\t\tfor (int i = 1; i < n; i++) {\n\t\t\t\tint diff = -arr[i] + arr[i - 1];\n\t\t\t\tif (diff > 0) {\n\t\t\t\t\tfirstMin += diff;\n\t\t\t\t}\n\n\t\t\t\tmaxDiff = Math.max(maxDiff, diff);\n\t\t\t}\n\n\t\t\tint secondMin = 0;\n\t\t\tfor (int i = 1; i < n; i++) {\n\t\t\t\tsecondMin += Math.min(maxDiff, arr[i - 1]);\n\t\t\t}\n\n\t\t\tSystem.out.println(\"Case #\" + tc + \": \" + firstMin + \" \" + secondMin);\n\t\t}\n\t}\n}\n",
                                            },
                                            {
                                                "isDirectory": false,
                                                "name": "googlejam1.p022.A.java",
                                                "path": "googlejam4_src/1/googlejam1.p022.A.java",
                                                "isClone": true,
                                                "content": "package googlejam1.p022;\nimport java.io.BufferedReader;\nimport java.io.InputStreamReader;\nimport java.util.StringTokenizer;\n\npublic class A {\n\n\tpublic static void main(String[] args) throws Exception {\n\t\tBufferedReader bf = new BufferedReader(new InputStreamReader(System.in));\n\n\t\tint ntests = Integer.parseInt(bf.readLine());\n\t\tfor (int tc = 1; tc <= ntests; tc++) {\n\t\t\tint n = Integer.parseInt(bf.readLine());\n\t\t\tint[] arr = new int[n];\n\t\t\tStringTokenizer st = new StringTokenizer(bf.readLine());\n\t\t\tfor (int i = 0; i < n; i++) {\n\t\t\t\tarr[i] = Integer.parseInt(st.nextToken());\n\t\t\t}\n\n\t\t\tint firstMin = 0;\n\t\t\tint maxDiff = 0;\n\t\t\tfor (int i = 1; i < n; i++) {\n\t\t\t\tint diff = -arr[i] + arr[i - 1];\n\t\t\t\tif (diff > 0) {\n\t\t\t\t\tfirstMin += diff;\n\t\t\t\t}\n\n\t\t\t\tmaxDiff = Math.max(maxDiff, diff);\n\t\t\t}\n\n\t\t\tint secondMin = 0;\n\t\t\tfor (int i = 1; i < n; i++) {\n\t\t\t\tsecondMin += Math.min(maxDiff, arr[i - 1]);\n\t\t\t}\n\n\t\t\tSystem.out.println(\"Case #\" + tc + \": \" + firstMin + \" \" + secondMin);\n\t\t}\n\t}\n}\n",
                                            },

                                            {
                                                "isDirectory": false,
                                                "name": "googlejam1.p041.A.java",
                                                "path": "googlejam4_src/1/googlejam1.p041.A.java",
                                                "isClone": true,
                                                "content": "package googlejam1.p041;\nimport java.io.BufferedWriter;\nimport java.io.File;\nimport java.io.FileWriter;\nimport java.io.IOException;\nimport java.util.Scanner;\n\npublic class A {\n\n  static final String FILENAME = \"A-large\";\n  static final String IN = FILENAME + \".in\";\n  static final String OUT = FILENAME + \".out\";\n  Scanner sc;\n  BufferedWriter out;\n\n  public static void main(String args[]) throws Exception {\n    A r = new A();\n\tr.sc = new Scanner(new File(A.IN));\n\tr.out = new BufferedWriter(new FileWriter(A.OUT));\n\tint t = Integer.parseInt(r.sc.nextLine());\n\tfor (int i = 1; i <= t; i++) {\n\t  r.out.write(\"Case #\" + i + \": \");\n\t  int n = r.sc.nextInt();\n\tint[] m = new int[n];\n\tfor(int i1 = 0; i1 < n; i1++) {\n\t  m[i1] = r.sc.nextInt();\n\t}\n\tint sum1 = 0;\n\tint cur = m[0];\n\tfor(int i4 = 1; i4 < n; i4++) {\n\t  if(m[i4] < cur) {\n\t    sum1 += cur - m[i4];\n\t  }\n\t  cur = m[i4];\n\t}\n\tr.out.write(Integer.toString(sum1) + \" \");\n\t\n\tint max = 0;\n\tfor(int i2 = 1; i2 < n; i2++) {\n\t  if(max < m[i2 - 1] - m[i2]) {\n\t    max = m[i2 - 1] - m[i2];\n\t  }\n\t}\n\tint sum2 = 0;\n\tfor(int i3 = 0; i3 < n - 1; i3++) {\n\t  sum2 += Math.min(max, m[i3]);\n\t}\n\tr.out.write(Integer.toString(sum2));\n\tr.out.newLine();\n\tr.out.flush();\n\t}\n\tr.sc.close();\n\tr.out.close();\n  }\n}\n",
                                            },

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

                    });
            }
            console.log('网络请求失败', this.fileTree)
        },
        MessageBox() {
            this.editVisible = true;
        },
        // 选择文件后做一些预处理
        beforeUpload(file) {
            // 自定义上传前的检查
            return new Promise((resolve, reject) => {
                // if (file.size > 500 * 1024) {
                //     this.$message.error('文件大小不能超过500KB');
                //     reject();
                // } else {
                //     resolve();
                // }
                resolve();
            });
        },
        handleFileChange(event) {
            const file = event.target.files[0];
            if (file) {
                // 文件验证
                // if (file.size > 500 * 1024) {
                //     alert('文件不能超过500KB');
                //     this.clearFileInput();
                //     return;
                // }
                if (!['application/zip', 'text/plain'].includes(file.type)) {
                    alert('只能上传zip/txt文件');
                    this.clearFileInput();
                    return;
                }

                this.fileList.push(file);
            }
        },
        // 文件变化时
        handleChange(event) {
            const file = event;
            if (file) {
                // 检查文件类型和大小
                if (!['.zip'].includes(file.name.slice(-4).toLowerCase())) {
                    this.$message.error('只能上传zip文件');
                    return;
                }
                // if (file.size > 500 * 1024) { // 500KB
                //     this.$message.error('文件大小不能超过500KB');
                //     return;
                // }

                this.fileList = [file]; 
            }
        },

        // 提交上传文件
        async submitUpload() {
            if (this.fileList.length === 0) {
                alert('请选择文件');
                return;
            } else {
                const formData = new FormData();
                this.fileList.forEach(file => {
                    formData.append('file',  file.raw || file);
                });

                this.loadingInstance = this.$loading({
                    lock: true,
                    text: '上传中...',
                    spinner: 'el-icon-loading',
                    background: 'rgba(0, 0, 0, 0.7)'
                });

                await this.uploadFile(formData)
            }

        },

        // 执行文件上传
        async uploadFile(formData) {
            try {
                const response = await fetch(`http://43.143.69.208:9002/upload`, {
                    method: 'POST',
                    body: formData
                });

                if (response.ok) {
                    const result = await response.json();
                    this.fileList = [];
                    this.editVisible = false
                    this.loadingInstance.close();
                    this.getDbList();

                } else {
                    this.loadingInstance.close();
                    this.editVisible = false; 
                    this.fileList = [];
                    this.editVisible = false;
                    alert('上传失败');
                }
            } catch (error) {
                alert('上传出错: ' + error.message);
                this.loadingInstance.close();
                this.fileList = ''
            }
        },

        clearFileInput() {
            this.$refs.fileInput.value = '';
            this.fileList = []; 
        },

        show(sub) {
            let content = `源代码：<br>${sub.content
                .trim() 
                .split('\n') 
                .join('<br>')
                }`; 
            const container1 = document.querySelector('.center1');
            container1.innerHTML = content
        },
        addOpenProperty(node) {

            if (node.isDirectory) {
                node.isOpen = false; 
            }
            if (Array.isArray(node.files) && node.files.length > 0) {
                node.files.forEach(childNode => this.addOpenProperty(childNode)); 
            }
        },

        triggerFileInput() {
            this.$refs.fileInput.click();
        },
        handleSizeChange(){},
        handleCurrentChange(){},
    }


}
</script>

<style scoped>
@import"../../../css/preDb.css";
</style>