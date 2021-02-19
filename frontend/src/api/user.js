import { createInstance } from "./index.js";

const instance = createInstance();
// const config = {
    //   headers: { "access-token": localStorage.getItem("access-token") }
    // };

function join(user, success, fail){
    instance
    .post('/user', JSON.stringify(user))
    .then(success)
    .catch(fail)
}

function modifyInfo(user, success, fail){
  instance
  .put('/user/modify', JSON.stringify(user))
  .then(success)
  .catch(fail)
}

function deleteInfo(user_no, success, fail){
  instance
  .delete(`/user/${user_no}`)
  .then(success)
  .catch(fail)
}

function login(user, success, fail) {
  instance.defaults.headers["access-token"] = window.localStorage.getItem(
    "access-token"
  );
  const body = {
    user_id: user.user_id,
    user_password: user.user_password
  };
  console.log("instance의 headers: ", instance.defaults.headers)
  instance
    .post("user/confirm/login", JSON.stringify(body))
    .then(success)
    .catch(fail);
}

function findUserId(user, success, fail){
  instance
  .post('/user/find/id', JSON.stringify(user))
  .then(success)
  .catch(fail)
}

function findPassword(user, success, fail){
  instance
  .post('/user/find/password', JSON.stringify(user))
  .then(success)
  .catch(fail)
}

function confirmId(user_id, success, fail){
  instance
  .get(`/user/confirm/${user_id}`)
  .then(success)
  .catch(fail)
}

function confirmPwd(user, success, fail){
  instance
  .post(`/user/confirm`, JSON.stringify(user))
  .then(success)
  .catch(fail)
}

async function findById(user_id, success, fail) {
  instance.defaults.headers["access-token"] = window.localStorage.getItem(
    "access-token"
  );
  await instance
    .get(`/user/info/${user_id}`)
    .then(success)
    .catch(fail);
}

export { join, modifyInfo, deleteInfo, login, findUserId, findPassword, confirmId, confirmPwd, findById };