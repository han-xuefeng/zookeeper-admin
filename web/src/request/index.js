import axios from 'axios'
import { message } from 'ant-design-vue'
import { getToken } from '@/utils'

const getBaseUrl = () => {
  if (process.env.VUE_APP_ENV == 'production') {
    return '';
  } else if(process.env.VUE_APP_ENV == 'debug') {
    return '/api';
  }else{
    return 'http://127.0.0.1:8083';
  }
}

let service=axios.create({
    baseURL:getBaseUrl(),
    timeout:30000,
    // withCredentials:true,
    validateStatus:function(status) {
      return status == 200;
    }
})


service.interceptors.request.use(
  function (config) {
    config.headers['X-Auth-Token'] = getToken()
    return config;
  }, function (error) {
    return Promise.reject(error);
  }
)

service.interceptors.response.use(
  function (response) {
    return response
  }, function (error) {
    handleError(error)
    return Promise.reject(error)
  }
)


const handleError = (error) => {

  if ("Network Error" === error.toString()) {
      message.error('网络异常');
      return false;
  }
  if (error.response !== undefined && error.response.status === 401) {
      window.location.href = '#/login';
      return false;
  }

  if (error.response !== undefined) {
      let data = error.response.data;
      message.error(`${data.message}`, 10);
      return false;
  }
  return true;
};

export default service