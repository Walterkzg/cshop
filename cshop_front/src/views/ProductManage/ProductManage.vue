<script setup>
import {getinfo,productAddService,updateProductService} from '@/api/product.js'
import { Plus } from '@element-plus/icons-vue'
import {Edit,Delete} from '@element-plus/icons-vue'
import { tableV2HeaderRowProps } from 'element-plus/es/components/table-v2/src/header-row';


import {ref} from 'vue'
//获取的商品数据
const Products = ref()


const username = ref("fubin")

const type = [
    {name:"显卡",value:1},
    {name:"cpu",value:2},    
    {name:"内存条",value:3},
    {name:"硬盘",value:4},
    {name:"主板",value:5},
    {name:"电源",value:6},
    {name:"散热",value:7},
    {name:"机箱",value:8},
    {name:"显示器",value:9},
    {name:"键鼠",value:10},
]


//分页条数据模型
const pageNum = ref(1)//当前页
const total = ref(20)//总条数
const pageSize = ref(3)//每页条数

//初始分类选择
const typeValue=ref('')

let res = ref()

//获取商品列表数据
const ProductList = async () => {
    let params = {
        pageNum: pageNum.value,
        pageSize: pageSize.value,
        typeValue: typeValue.value,
        username:"fubin"
    }

    res = await getinfo('product/getPage',params)

    //渲染视图
    total.value = res.data.total
    Products.value = res.data.items
}


let res1=ref()
//添加商品时的校验函数
const ProductList_add = async () => {
    let params = {
        pageNum: 1,
        pageSize: 1,
        pid:ALL
    }
    res1 = await getinfo('product/getPage',params)
    console.log(typeof res1.data.total,res1.data.total)
}

//添加商品函数
const addProduct =async ()=>{
    ProductList_add()
    Product.value.pid = ALL
    let result = await productAddService(Product.value)

    ELMessage.success(result.msg? result.msg:'添加成功')

    visibleDrawer.value = false
    //刷新列表
    ProductList()
}

ProductList()


//当每页条数发生了变化，调用此函数
const onSizeChange = (size) => {
    pageSize.value = size
    ProductList()
}
//当前页码发生变化，调用此函数
const onCurrentChange = (num) => {
    pageNum.value = num
    ProductList()
}


//添加商品数据模型
const Product = ref({
    'username':username.value,
    'pid':'',
    'pname':'',
    'ptype':'',
    'price':'',
    'photo':'',
    'pstock':''
})



//控制抽屉显示
const visibleDrawer = ref(false)

//控制弹窗显示
const dialogVisible = ref(false)
const showDialog = (row) =>{
    dialogVisible.value = true,
    Product.value.pname = row.pname
    Product.value.price = row.price
    Product.value.ptype = row.ptype
    Product.value.pid = row.pid

    // console.log(Product.value)
}

//更新数据函数
const updateProduct = () =>{
    // console.log(Product.value)
    let result = updateProductService(Product.value)

    ProductList()
    dialogVisible.value=false
}

//将类型转化为字符串显示
const P_type =function(row){
    if(row.ptype == 1){
        return '显卡'   
    }else if(row.ptype==2){
        return 'cpu'
    }else if(row.ptype==3){
        return '内存条'
    }else if(row.ptype==4){
        return '硬盘'
    }else if(row.ptype==5){
        return '主板'
    }else if(row.ptype==6){
        return '电源'
    }else if(row.ptype==7){
        return '散热'
    }else if(row.ptype==8){
        return '机箱'
    }else if(row.ptype==9){
        return '显示器'
    }else if(row.ptype==10){
        return '键鼠'
    }
}

const centerDialogVisible=ref(false)

</script>

<template>
    <!-- 下架商品弹窗 -->
        <el-dialog v-model="centerDialogVisible" title="Warning" width="500" center>
            <span>确认下架该商品吗？</span>
            <template #footer>
            <div class="dialog-footer">
                <el-button @click="centerDialogVisible = false">取消</el-button>
                <el-button type="primary" @click="centerDialogVisible = false">确认</el-button>
            </div>
            </template>
        </el-dialog>
     <!-- 修改商品弹窗 -->
     <el-dialog v-model="dialogVisible" :title='修改商品信息' width="30%">
            <el-form :model="categoryModel" :rules="rules" label-width="100px" style="padding-right: 30px">
                <el-form-item label="商品名称" prop="Products.pname">
                    <el-input v-model="Product.pname" minlength="1" maxlength="10"></el-input>
                </el-form-item>
                <el-form-item label="商品价格" prop="price">
                    <el-input v-model="Product.price" minlength="1" maxlength="15"></el-input>
                </el-form-item>
                <el-form-item label="商品分类">
                    <el-select placeholder="请选择" v-model="Product.ptype">
                        <el-option v-for="c in type" :key="c.name" :label="c.name" :value="c.value">
                        </el-option>
                    </el-select>
                </el-form-item>
                <el-form-item label="商品预览图" prop="pimg">

                    <!-- 
                        auto-upload:设置是否自动上传
                        action:设置服务器接口路径
                        name:设置上传的文件字段名
                        headers:设置上传的请求头
                        on-success:设置上传成功的回调函数
                    -->
                    <el-upload class="avatar-uploader" v-model="Product.pimg">
                    <img v-if="Product.photo" :src="Product.photo" class="avatar" />
                    <el-icon v-else class="avatar-uploader-icon">
                        <Plus />
                    </el-icon>
                </el-upload>
            </el-form-item>
            </el-form>
            <template #footer>
                <span class="dialog-footer">
                    <el-button @click="dialogVisible = false">取消</el-button>
                    <el-button type="primary" @click="updateProduct"> 确认 </el-button>
                </span>
            </template>
        </el-dialog>

    <el-card class="page-container">
        <template #header>
            <div class="header">
                <span>商品管理</span>
                <div class="extra">
                    <el-button type="primary" @click="visibleDrawer = true">添加商品</el-button>
                </div>
            </div>
        </template>
        <!-- 搜索表单 -->
         <el-form inline>
            <el-form-item label="商品分类：">
                <el-select placeholder="请选择" v-model="typeValue" style="width:200px">
                    <el-option v-for="item in type" :label=item.name :value=item.value key:item.name>
                    </el-option>
                </el-select>
            </el-form-item>
            <el-form-item>
                <el-button type="primary" @click="ProductList">搜索</el-button>
                <el-button @click="typeValue = ''">重置</el-button>
            </el-form-item>
        </el-form>
        <!-- 商品列表 -->
         <el-table :data="Products" style="width: 100%" >
            <el-table-column label="商品名" width="400" prop="pname"></el-table-column>
            <el-table-column label="商品类型" prop="ptype" :formatter="P_type"></el-table-column>
            <el-table-column label="单价" prop="price"> </el-table-column>
            <el-table-column label="商品预览图" prop="pimg" >
                <!-- <template scope="scope">
                    <img v-for="item in scope.pimg" :src="item"/>
                </template> -->
            </el-table-column>
            <el-table-column label="操作" width="100">
                <template #default="{ row }">
                    <el-button :icon="Edit" circle plain type="primary" @click="showDialog(row)"></el-button>
                    <el-button :icon="Delete" circle plain type="danger" @click="centerDialogVisible = true"></el-button>
                </template>
            </el-table-column>
            <template #empty>
                <el-empty description="没有数据" />
            </template>
        </el-table> 
        <!-- 分页条 -->
        <el-pagination v-model:current-page="pageNum" v-model:page-size="pageSize" :page-sizes="[3, 5, 10, 15]"
            layout="jumper, total, sizes, prev, pager, next" background :total="total" @size-change="onSizeChange"
            @current-change="onCurrentChange" style="margin-top: 20px; justify-content: flex-end" />

        <!-- 抽屉 -->
        <el-drawer v-model="visibleDrawer" title="添加商品" direction="rtl" size="50%">
            <!-- 添加商品表单 -->
            <el-form :model="ProductModel" label-width="100px">
                <el-form-item label="商品名称">
                    <el-input v-model="Product.pname" placeholder="请输入商品名"></el-input>
                </el-form-item>
                <el-form-item label="商品分类">
                    <el-select placeholder="请选择" v-model="Product.ptype">
                        <el-option v-for="c in type" :key="c.name" :label="c.name" :value="c.value">
                        </el-option>
                    </el-select>
                </el-form-item>
                <el-form-item label="商品单价">
                    <el-input v-model="Product.price" placeholder="请输入商品单价"></el-input>
                </el-form-item>
                <el-form-item label="商品预览图">

                    <!-- 
                        auto-upload:设置是否自动上传
                        action:设置服务器接口路径
                        name:设置上传的文件字段名
                        headers:设置上传的请求头
                        on-success:设置上传成功的回调函数
                     -->
                   
                    <el-upload class="avatar-uploader" :auto-upload="true" :show-file-list="false"
                    action="/api/upload"
                    name="file"
                    :on-success="uploadSuccess"
                    >
                        <img v-if="Product.photo" :src="Product.photo" class="avatar" />
                        <el-icon v-else class="avatar-uploader-icon">
                            <Plus />
                        </el-icon>
                    </el-upload>
                </el-form-item>

                <el-form-item>
                    <el-button type="primary" @click="addProduct">添加</el-button>
                </el-form-item>
            </el-form>
        </el-drawer>
    </el-card>
</template>
<style lang="scss" scoped>
.page-container {
    min-height: 100%;
    box-sizing: border-box;

    .header {
        display: flex;
        align-items: center;
        justify-content: space-between;
    }
}

/* 抽屉样式 */
.avatar-uploader {
    :deep() {
        .avatar {
            width: 178px;
            height: 178px;
            display: block;
        }

        .el-upload {
            border: 1px dashed var(--el-border-color);
            border-radius: 6px;
            cursor: pointer;
            position: relative;
            overflow: hidden;
            transition: var(--el-transition-duration-fast);
        }

        .el-upload:hover {
            border-color: var(--el-color-primary);
        }

        .el-icon.avatar-uploader-icon {
            font-size: 28px;
            color: #8c939d;
            width: 178px;
            height: 178px;
            text-align: center;
        }
    }
}

.editor {
    width: 100%;

    :deep(.ql-editor) {
        min-height: 200px;
    }
}
        img{
            width:50px;
        }
</style>