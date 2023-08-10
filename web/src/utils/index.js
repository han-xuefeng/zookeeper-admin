import { TOKEN } from "@/constant";

// 单独写 以后可以控制token的存取地方 和实现方式
export function setToken(token) {
  localStorage.setItem(TOKEN, token)
}

export const getToken = function(){
  return localStorage.getItem(TOKEN)
}

export const clearToken = () => {
  return localStorage.removeItem(TOKEN)
}