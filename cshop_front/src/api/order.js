import request from '@/utils/request.js'
import instance from '@/utils/request'

//订单列表查询
export const getinfo = (URL, data) => {
    const params = new URLSearchParams()
    for (let key in data) {
        params.append(key, data[key])
    }
    URL = URL + '?' + params.toString()
    return instance.get(URL)
}