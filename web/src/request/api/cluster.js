import service from ".."

export function clustersPaging(data){
  return service({
      method:"get",
      url:"/clusters/paging",
      params:data
  })
}

export function clustersConnect(data){
  return service({
      method:"get",
      url:"/clusters/connect",
      params:data
  })
}