<script setup>
import { getinfo } from '@/api/order'
import {ref} from 'vue'
//订单状态值
const stateValue = ref('3')

const state=[
    {name:"已完成",value:"1"},
    {name:"未完成",value:"2"}
]

//当前页数
const pageNum=ref(1)
//总条数
const total = ref(20)
//每页条数
const pageSize = ref(3)

//订单数据模型
const order = ref()

//获取订单列表数据
const orderList=async()=>{
    let params={
        pageNum:pageNum.value,
        pageSize:pageSize.value,
        state:stateValue.value,
        username:'fubin'
    }
    let result = await getinfo('order/getOrderByPage',params)

    total.value = result.data.total
    order.value = result.data.items
    console.log(result.data.items)
    console.log(order.value)
}

orderList();


</script>

<template>
<el-form inline>
    <el-card class="page-container"> 
        <template #header>
            <div class="header">
                <span>订单管理</span>
            </div>
        </template>
        <!-- 搜索表单 -->
         <el-form inline>
            <el-form-item label="订单状态：">
                <el-select placeholder="请选择" v-model="stateValue" style="width:200px">
                    <el-option v-for="item in state" :label=item.name :value=item.value key:item.name>
                    </el-option>
                </el-select>
            </el-form-item>
            <el-form-item>
                <el-button type="primary" @click="orderList">搜索</el-button>
                <el-button @click="typeValue = ''">重置</el-button>
            </el-form-item>
        </el-form>
        <!-- 订单列表 -->
        <el-table :data="order" style="width: 100%">
                <el-table-column label="买家" prop="uname"></el-table-column>
                <el-table-column label="商品名" prop="pname"></el-table-column>
                <el-table-column label="数量" prop="num"> </el-table-column>
                <el-table-column label="总价" prop="price"></el-table-column>
                <el-table-column label="状态" prop="state"></el-table-column>
                <el-table-column label="下单时间" prop="otime"></el-table-column>
                <template #empty>
                    <el-empty description="没有数据" />
                </template>
            </el-table>
        <!-- 分页条 -->
        <el-pagination v-model:current-page="pageNum" v-model:page-size="pageSize" :page-sizes="[3, 5, 10, 15]"
            layout="jumper, total, sizes, prev, pager, next" background :total="total" @size-change="onSizeChange"
            @current-change="onCurrentChange" style="margin-top: 20px; justify-content: flex-end" />
    </el-card>
</el-form>
</template>