import service from ".."

export function clusterDataList(data){
  return service({
      method:"get",
      url:"/clusterData/list",
      params:data
  })
}


export function clusterDataItem(data){
  return service({
      method:"get",
      url:"/clusterData/item",
      params:data
  })
}


export function clusterDataCreate(data, params){
  return service({
      method:"post",
      url:"/clusterData/create",
      data:data,
      params:params
  })
}

export function clusterDataDelete(data, params){
  return service({
      method:"post",
      url:"/clusterData/delete",
      data:data,
      params:params
  })
}