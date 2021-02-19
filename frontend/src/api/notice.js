import { createInstance } from "./index.js";

const instance = createInstance();

function listNoticepage(success, fail) {
    instance
        .get(`/noticepage`)
        .then(success)
        .catch(fail)
}

function getNoticepage(notice, success, fail) {
    instance
        .get(`/noticepage/'3'`, JSON.stringify(notice))
        .then(success)
        .catch(fail)
}

function writeNoticepage(notice, success, fail) {
    instance
        .post('/noticepage', JSON.stringify(notice))
        .then(success)
        .catch(fail)
}

function modifyNoticepage(notice, success, fail) {
    instance
        .put('/noticepage', JSON.stringify(notice))
        .then(success)
        .catch(fail)
}

function deleteNoticepage(notice_no, success, fail) {
    instance
        .delete(`/noticepage/${notice_no}`)
        .then(success)
        .catch(fail)
}

export { listNoticepage, getNoticepage, writeNoticepage, modifyNoticepage, deleteNoticepage };