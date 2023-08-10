<template>
    <a-layout-header>
      <div class="km-header">
        <div class="km-header-logo">
          <router-link to="/">Zookeeper Admin</router-link>
        </div>
        <div>
          <div className='km-header-right'>
            <a-dropdown>
              <a class="ant-dropdown-link">
                {{state.userInfo.username}}
                <DownOutlined />
              </a>
              <template #overlay>
                <a-menu>
                  <a-menu-item>
                    <router-link to="/changePassword">Change Password</router-link>
                  </a-menu-item>
                  <a-menu-item @click="userLogout">
                    Login Out
                  </a-menu-item>
                </a-menu>
              </template>
            </a-dropdown>
          </div>
          <div className='km-header-right'>
            <span class='km-header-right-item'>
                <a-tooltip title="star">
                  <a-button 
                    type="text"
                    target="block"
                    href="https://github.com/han-xuefeng"
                    style="color: white;"
                  >
                    <template #icon><GithubOutlined /></template>
                  </a-button>
                </a-tooltip>
            </span>
          </div>
        </div>
        
      </div>
    </a-layout-header>
</template>
<script setup>
  import { GithubOutlined,DownOutlined } from '@ant-design/icons-vue'
  import { clearToken } from '@/utils';
  import { info, logout } from '@/request/api/user'
  import { onMounted, reactive } from 'vue';

  const state = reactive({"userInfo":{}})

  const userLogout = async () => {
    let res = await logout();
    clearToken()
    window.location.reload()
  }

  onMounted(async ()=>{
    let res = await info()
    console.log(res);
    state.userInfo = res.data.data
  })
  

</script>
<style lang="less" scoped>

.ant-layout-header{
  padding: 0%;
  .km-header{
    height: 100%;
    width: 80%;
    margin: 0 auto;
    display: flex;
    align-items: center;
    height: 100%;
    justify-content: space-between;
    .km-header-logo {
      font-size: 18px;
      font-weight: 500;
      cursor: pointer;
      color: white;
    }
    .km-header-right {
        text-align: right;
        height: 100%;
        margin: 0 8px;
        float: left;
        color: white;
        .ant-dropdown-link{
          display: block;
          height: 100%;
          color: white;
        }
        .km-header-right-item {
          cursor: pointer;
          display: inline-flex;
          align-items: center;
          justify-content: center;
          font-size: 16px;
          vertical-align: middle;
          height: 100%;
        }
      }
  }
}
</style>