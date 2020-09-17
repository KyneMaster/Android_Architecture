## PMS USER模块

### 登录模块

1. 登录主页面 - LoginActivity
    - 中间输入和登录按钮部分采用Fragment片段点击切换，对应AccountLoginFragment和PhoneLoginFragment
    - LoginActivity跳转需要AppRoute，而User模块内部跳转使用Intent

2. AccountLoginFragment
    - 按钮初始化不可点击，80透明度。当账号密码填写完整，设置可点击。
    - EditText的监听做的统一处理：LoginTextChanged.class监听输入事件，onEditorAction()监听做软键盘回车监听。
    - 登录两种情况，1、直接登录成功；2、账号未激活，返回手机号，需要验证该手机号，即发生验证码，跳转SSCodeActivity，*当前login页面不关闭*

3. PhoneLoginFragment
    - 初始化button不可点击，80透明度，当手机号11位，并且勾选阅读须知，可点击登录，即发送验证码，*当前login页面不关闭*
    - 初始化服务协议和隐私政策默认勾选
    - 登录注册一体

4. SSCodeActivity
    - 自定义SSCodeInputView，输入完成触发回调事件
    - type类型判断是激活账号，还是验证码登录，对应展示以及路由不同
    - 激活成功跳转主页面，关闭当前所有activity实例
    - 手机号登录未注册，需要携带手机号跳转注册页面RegisterActivity，*关闭此页面*
    - 可重发验证码

5. RegisterActivity
    - 按钮同登录模块一致。初始化不可点击。
    - EditText监听统一，RegisterTextChanged.class监听输入。onEditorAction()监听虚拟键回车，光标聚焦下一个EditText
    - 完成跳转Main，清空此前所有Activity

6. 网络Cookie
    - 账号密码登录成功，返回Cookie
    - 账号激活成功，返回Cookie
    - 手机号快捷登录成功，返回Cookie
    - 手机号注册成功，返回Cookie
    - 此处添加了LoginHttpInterceptor统一拦截，获取到Cookie即CookieProvider缓存
    - 返回cookie成功，即表示登录成功，然后获取用户信息保存。

7. 隐私声明和服务协议
    - 使用WebViewFragment

// @deprecated 弃用。组织状态发生变化情况，移动端缓存无法获取，只能每次打开app做组织的校验。
// 8. 跳转主页面前需要判断用户组织，并获取用户应用
//    - 增加组织路由拦截器OrgRouteInterceptor，跳转主页面、已登录，并且没有加入组织，拦截跳转(防止用户未填写邀请码退出后再次登录情况)

9. user作为一个独立模块，LoginActivity为该模块主页，其他页面跳转后即刻被关闭，只有当该模块完成任务时，即登录完成，跳转主页面时，该模块所有activity被关闭
