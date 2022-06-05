<template>
  <div style="width: 70%; padding:10px; margin: 50px auto;
  height: 300px;border: 5px solid #4aaddc;border-radius: 50px">
    <div style="display: block;position: relative;font-size: 80px;color: darkseagreen;
      font-family: Georgia,serif;top: 80px;text-align: center;">欢迎使用教考分离系统!
    </div>

    <div v-if="data !== []" class="Echarts" style="position: absolute;left: 250px;top: 450px;">
      <div id="daily" :style="{width: '1200px', height: '250px'}"></div>
    </div>
  </div>
</template>

<script>
import * as echarts from "echarts";
import request from "@/utils/request";

export default {
  data() {
    return {
      id: '',
    }
  },
  mounted() {
    var i = 0;
    var id1 = setInterval(this.daily, 100);
    var id2 = setInterval( myTimer , 100);
    function myTimer() {
        i++;
        if(i === 5) {
          clearInterval(id1);
          clearInterval(id2);
        }
    }
  },
  methods: {
    daily() {
      let chartDom = document.getElementById('daily');
      let myChart = echarts.init(chartDom);
      let myDate = new Date();
      let s = myDate.getFullYear();

      function getVirtulData(year) {
        var user = JSON.parse(sessionStorage.getItem("user"))
        // console.log(user)
        var date = +echarts.number.parseDate(year + '-01-01');
        var end = +echarts.number.parseDate(+year + 1 + '-01-01');
        var dayTime = 3600 * 24 * 1000;
        var data = []
        request.get("/api/local_record", {
          params: {
            code: user.code,
            dateTime: date,
          }
        }).then(res => {
          // console.log(res.data)
          for(var time = date; time < end; time += dayTime) {
            data.push([
              echarts.format.formatTime('yyyy-MM-dd', time),
              Math.floor(res.data[(time - date) / dayTime])
            ]);
          }
          sessionStorage.setItem("data", JSON.stringify(data))
        })
        return JSON.parse(sessionStorage.getItem("data"))
      }

      let option;
      option = {
        title: {
          top: 30,
          left: 'center',
          text: '登录记录'
        },
        tooltip: {},
        visualMap: {

          type: 'piecewise',
          splitNumber: 2,
          pieces: [
            {min: 0, max: 0, label: '未登录'},
            {min: 1, max: 1, label: '活跃'}
          ],
          color: ['#12a131', '#eadddd',],
          orient: 'horizontal',
          left: 'center',
          top: 65
        },
        calendar: {
          top: 120,
          left: 30,
          right: 30,
          cellSize: ['auto', 13],
          range: s,
          itemStyle: {
            borderWidth: 0.5
          },
          yearLabel: {show: false}
        },
        series: {
          type: 'heatmap',
          coordinateSystem: 'calendar',
          data: getVirtulData(s),
        }
      };

      // option && myChart.setOption(option);
      myChart.setOption(option);
    }
  },
  name: "pc_index"
}
</script>

<style scoped>

</style>


