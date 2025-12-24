<template>
  <div>
    <el-breadcrumb separator="/" class="elBreadcrumb">
      <el-breadcrumb-item 
        v-for="(item, index) in breadcrumbs" 
        :key="index"
        :to="index === 0 ? { path: '/home/mp' } : item.path"
      >
        {{ item.meta.breadcrumb }}
      </el-breadcrumb-item>
    </el-breadcrumb>
  </div>
</template>

<script>
export default {
  props: {
    routes: {
      type: Array,
      required: true
    }
  },
  computed: {
    breadcrumbs() {
      return this.routes.filter(route => route.meta && route.meta.breadcrumb);
    }
  }
}
</script>

