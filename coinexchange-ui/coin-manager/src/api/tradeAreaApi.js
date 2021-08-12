import request from '@/utils/request';
import {formUtils} from '@/utils/formUtil'

export const tradeAreaApi = {

  /**
   * 币币交易获取交易区域列表
   * @param current
   * @param size
   */
  getTradeAreaList(form, current, size) {
    let params = {}
    formUtils.formDateRange(params,form);
    params.current = current
    params.size = size
    return request({
      url: '/trade/tradeAreas',
      method: 'get',
      params: params
    })
  },
  /**
   * 币币交易新增交易区域
   */
  createdTradeArea(data) {
    return request({
      url: '/trade/tradeAreas',
      method: 'post',
      data: data
    })
  },
  /**
   * 币币交易修改交易区域
   */
  updateTradeArea(data) {
    return request({
      url: '/trade/tradeAreas',
      method: 'patch',
      data: data
    })
  },

  /**
  *交易区状态设置  /tradeArea/setStatus交易区状态设置(status:1启用 0禁用)
  **/
  setTradeAreaStats(params){
    return request({
      url:'/trade/tradeAreas/status',
      method:'post',
      data:params
    })
  },

  /**
  *交易区删除
  **/
  deleteTradeArea(data){
    return request({
      url:'/trade/tradeAreas/delete',
      method:'post',
      data:data
    })
  },

  /**
  * 创新交易 获取交易区域列表 /forex/area
  */
  getInnovateTradeAreaList(form, current, size) {
    let params = {}
    formUtils.formDateRange(params,form);
    params.current = current
    params.size = size
    return request({
      url: '/forex/area',
      method: 'get',
      params: params
    })
  },

  /**
   * 币币交易新增交易区域
   */
  createdInnovateTradeArea(data) {
    return request({
      url: '/forex/area',
      method: 'post',
      data: data
    })
  },
  /**
   * 币币交易修改交易区域
   */
  updateInnovateTradeArea(data) {
    return request({
      url: '/forex/area',
      method: 'put',
      data: data
    })
  },

}
