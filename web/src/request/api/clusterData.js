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