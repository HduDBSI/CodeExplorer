<template>
  <div>
    <el-container class="elHeader1">
      <!--左侧导航栏-->
      <NavMenu />
      <el-container>
        <!--右侧主体-->
        <!--头部面包屑-->
        <el-header ><DyTabs :routes="$route.matched"/></el-header>
        <!--主体-->
        <el-main class="body"><router-view /></el-main>
      </el-container>
    </el-container>
  </div>
</template>

<script>
import NavMenu from './navMenu.vue'
import DyTabs from './DynamicTabs.vue'
export default {
  data() {
    return {

    }

  },
  methods: {

  },
  components: {
    NavMenu,
    DyTabs
  }
}
</script>
<style>
@import"../../css/menu.css";

</style>