import service from ".."

export function login(data){
  return service({
      method:"POST",
      url:"/login",
      data:data
  })
}

export function info(){
  return service({
    method:'GET',
    url:'/info'
  })
}

export function logout(){
  return service({
    method:'POST',
    url:'/logout'
  })
}