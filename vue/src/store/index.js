import { createStore } from 'vuex'

export default createStore({
  state: {
    headerFlag: 0,
  },
  mutations: {
    update(state) {
      state.headerFlag = !state.headerFlag
    }
  },
  actions: {
  },
  modules: {
  }
})
