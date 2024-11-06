import instance from '@/utils/request'


//商品列表查询

export const getinfo = (URL, data) => {
    const params = new URLSearchParams()
    for (let key in data) {
        params.append(key, data[key])
    }
    URL = URL + '?' + params.toString()
    return instance.get(URL)
}
//添加商品
export const productAddService = (productData) => {
    return instance.put('Product/add', productData)
}

//更新商品数据
export const updateProductService = (productData) => {
    console.log(productData)
    return instance.put('Product/update', productData)
}