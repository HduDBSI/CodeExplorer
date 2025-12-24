<template>
    <div class="el-menu-vertical-demo">
        <el-row class="tac">
            <el-col :span="12">
                <el-menu :default-active="activeMenu" router text-color="#333" active-text-color="#409EFF">
                    <el-menu-item>
                        <img src='@/assets/logo.png' class="custom-icon" />
                        <span slot="title" class="title1">CodeExplorer</span>
                    </el-menu-item>
                    <el-menu-item index="/home/mp">
                        <img :src="activeMenu === '/home/mp' ? require('@/assets/点击后/模型仓库@2x.png') : require('@/assets/点击前/模型仓库@2x.png')"
                            alt="CodeExplorer Icon" class="custom-icon" />
                        模型仓库</el-menu-item>
                    <el-menu-item index="/home/pre-db">
                        <img :src="activeMenu === '/home/pre-db' ? require('@/assets/点击后/代码数据集@2x.png') : require('@/assets/点击前/已处理数据集@2x.png')"
                            alt="CodeExplorer Icon" class="custom-icon" />
                        代码数据集</el-menu-item>
                    <el-submenu index="1" >
                        <template slot="title">
                            <img :src="activeMenu === '' ? require('@/assets/点击后/任务列表@2x.png') : require('@/assets/点击前/任务列表@2x.png')"
                                alt="CodeExplorer Icon" class="custom-icon" />
                            任务列表
                        </template>
                        <el-menu-item index="/home/pre">
                            <img :src="activeMenu === '/home/pre' ? require('@/assets/点击后/预处理任务列表@2x.png') : require('@/assets/点击前/预处理任务列表@2x.png')"
                                alt="CodeExplorer Icon" class="custom-icon" />
                            预处理任务列表</el-menu-item>
                        <el-submenu index="2">
                            <template slot="title">
                                <!-- <img :src="activeMenu === '/home/train' ? require('@/assets/点击后/训练任务列表@2x.png') : require('@/assets/点击前/训练任务列表@2x.png')"
                                    alt="CodeExplorer Icon" class="custom-icon" /> -->
                                <img :src="require('@/assets/点击前/训练任务列表@2x.png')" class="custom-icon" />
                                训练任务列表
                            </template>
                            <el-menu-item :index="'/home/cltrain'">
                                <img :src="activeMenu === '/home/cltrain' ? require('@/assets/点击后/代码克隆检测@1x.png') : require('@/assets/点击前/代码克隆检测@2x.png')"
                                    alt="CodeExplorer Icon" class="custom-icon" />
                                代码克隆检测
                            </el-menu-item>
                            <el-menu-item index="/home/ywtrain">
                                <img :src="activeMenu === '/home/ywtrain' ? require('@/assets/点击后/代码异味检测@2x.png') : require('@/assets/点击前/代码异味检测@2x.png')"
                                    alt="CodeExplorer Icon" class="custom-icon" />
                                代码异味检测
                            </el-menu-item>
                            <el-menu-item index="/home/ldtrain">
                                <img :src="activeMenu === '/home/ldtrain' ? require('@/assets/点击后/代码漏洞检测@2x.png') : require('@/assets/点击前/代码漏洞检测@2x.png')"
                                    alt="CodeExplorer Icon" class="custom-icon" />
                                代码漏洞检测
                            </el-menu-item>
                        </el-submenu>
                        <el-submenu index="3">
                            <template slot="title">
                                <!-- <img :src="activeMenu === '/home/test' ? require('@/assets/点击后/测试任务列表@2x.png') : require('@/assets/点击前/测试任务列表@2x.png')"
                                    alt="CodeExplorer Icon" class="custom-icon" /> -->
                                <img :src="require('@/assets/点击前/测试任务列表@2x.png')" class="custom-icon" />
                                测试任务列表
                            </template>
                            <el-menu-item index="/home/cltest">
                                <img :src="activeMenu === '/home/cltest' ? require('@/assets/点击后/代码克隆检测@1x.png') : require('@/assets/点击前/代码克隆检测@2x.png')"
                                    alt="CodeExplorer Icon" class="custom-icon" />
                                代码克隆检测
                            </el-menu-item>
                            <el-menu-item index="/home/ywtest">
                                <img :src="activeMenu === '/home/ywtest' ? require('@/assets/点击后/代码异味检测@2x.png') : require('@/assets/点击前/代码异味检测@2x.png')"
                                    alt="CodeExplorer Icon" class="custom-icon" />
                                代码异味检测
                            </el-menu-item>
                            <el-menu-item index="/home/ldtest">
                                <img :src="activeMenu === '/home/ldtest' ? require('@/assets/点击后/代码漏洞检测@2x.png') : require('@/assets/点击前/代码漏洞检测@2x.png')"
                                    alt="CodeExplorer Icon" class="custom-icon" />
                                代码漏洞检测
                            </el-menu-item>

                        </el-submenu>
                    </el-submenu>
                    <!-- <el-menu-item index="/home/temporary">
                        临时效果演示
                    </el-menu-item> -->
                </el-menu>
            </el-col>
        </el-row>

    </div>
</template>

<script>
export default {
    watch: {
        '$route'(to, from) {
            if (from.path === '/home/pre/predisplay' && to.path === '/home/pre') {
                this.$nextTick(() => {
                    window.location.reload();
                });
            } else if (from.path === '/home/cltrain/cltraindisplay' && to.path === '/home/cltrain') {
                this.$nextTick(() => {
                    window.location.reload();
                });
            } else if (from.path === '/home/test/cltestdisplay' && to.path === '/home/cltest') {
                this.$nextTick(() => {
                    window.location.reload();
                });
            }
        }
    },
    computed: {
        activeMenu() {
            return this.$route.path;
        }
    }
}
</script>
<style>
@import "../../css/navMenu.css";
</style>