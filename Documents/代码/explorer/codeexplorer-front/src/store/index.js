// store/index.js
import Vue from 'vue';
import Vuex from 'vuex';

Vue.use(Vuex);

export default new Vuex.Store({
  state: {
    globalVariable: 'Hello, World!'
  },
  mutations: {
    setGlobalVariable(state, newValue) {
      state.globalVariable = newValue;
    }
  },
  actions: {
    updateGlobalVariable({ commit }, newValue) {
      commit('setGlobalVariable', newValue);
    }
  },
  getters: {
    globalVariable: state => state.globalVariable
  }
});
