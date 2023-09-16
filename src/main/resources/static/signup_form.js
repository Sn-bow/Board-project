const id = document.querySelector(".id")
const pwd = document.querySelector(".pwd")
const pwdCh = document.querySelector(".pwd-check")
const email = document.querySelector(".email")
const signUpBtn = document.querySelector(".signup-btn")
// err document
const idErr = document.querySelector(".id-err")
const pwdErr = document.querySelector(".pwd-err")
const pwdErr2 = document.querySelector(".pwd-err2")
const pwdChErr = document.querySelector(".pwd-check-err")
const emailErr = document.querySelector(".email-err")


// TODO : 정규식
// TODO : 버튼 활성화
// TODO : 이벤트 조건
// TODO : 함수작성


// 정규식
const pwdExp = /^(?=.*[a-zA-Z])(?=.*[!@#$%^*+=-])(?=.*[0-9]).{8,15}$/;
const emailExp = /^[0-9a-zA-Z]([-_\.]?[0-9a-zA-Z])*@[0-9a-zA-Z]([-_\.]?[0-9a-zA-Z])*\.[a-zA-Z]{2,3}$/i;

// input status
let idState = false;
let pwdState = false;
let pwdChState = false;
let emailState = false;

// function
// button style function
const signUpBtnStyle = () => {
    signUpBtn.addEventListener("mouseover", () => {
        signUpBtn.style.cursor = "pointer";
        signUpBtn.style.backgroundColor = "#a3a1a1";
        signUpBtn.style.color = "#e3dede";
        signUpBtn.style.transition = "all 0.3s ease-in-out";
    });
    signUpBtn.addEventListener("mouseout", () => {
        signUpBtn.style.cursor = "pointer";
        signUpBtn.style.backgroundColor = "white";
        signUpBtn.style.color = "#6e6e6e";
        signUpBtn.style.transition = "all 0.3s ease-in-out";
    });
};

const signUpDisableBtnStyle = () => {
    signUpBtn.addEventListener("mouseover", () => {
        signUpBtn.style.cursor = "default";
        signUpBtn.style.backgroundColor = "white";
        signUpBtn.style.color = "#6e6e6e";
        signUpBtn.style.transition = "all 0.3s ease-in-out";
    });
    signUpBtn.addEventListener("mouseout", () => {
        signUpBtn.style.cursor = "default";
        signUpBtn.style.backgroundColor = "white";
        signUpBtn.style.color = "#6e6e6e";
        signUpBtn.style.transition = "all 0.3s ease-in-out";
    });
};

// 확인 버튼 함수
const btnActHandler = () => {
    if (idState && pwdState && pwdChState && emailState) {
        signUpBtn.disabled = false;
        signUpBtnStyle();
    } else {
        signUpBtn.disabled = true;
        signUpDisableBtnStyle();
    }
}

window.onload = () => {
    btnActHandler();
}

id.addEventListener("keyup", (e) => {
    if (e.target.value != "") {
        idState = true;
        idErr.style.display = "none";
    } else {
        idState = false;
        idErr.style.display = "block";
    }
    btnActHandler();
})

let pwdTemp = "";
let pwdChTemp = "";
pwd.addEventListener("keyup", (e) => {
    pwdTemp = e.target.value;
    if (pwdExp.test(pwdTemp)) {
        pwdState = true;
        pwdErr2.style.display = "none";
    } else {
        pwdState = false;
        pwdErr2.style.display = "block";
    }

    if (pwdTemp == "") {
        pwdState = false;
        pwdErr.style.display = "block";
    } else {
        pwdState = true;
        pwdErr.style.display = "none";
    }

    // password check
    if (pwdChTemp == pwdTemp) {
        pwdChState = true;
        pwdChErr.style.display = "none";
    } else if (pwdChTemp != pwdTemp || e.target.value == "") {
        pwdChState = false;
        pwdChErr.style.display = "block";
    }

    btnActHandler();
})

pwdCh.addEventListener("keyup", (e) => {
    pwdChTemp = e.target.value;
    if (pwdChTemp == pwdTemp) {
        pwdChState = true;
        pwdChErr.style.display = "none";
    } else if (pwdChTemp != pwdTemp || e.target.value == "") {
        pwdChState = false;
        pwdChErr.style.display = "block";
    }
    btnActHandler();
})

email.addEventListener("keyup", (e) => {
    if (emailExp.test(e.target.value)) {
        emailState = true;
        emailErr.style.display = "none";
    } else {
        emailState = false;
        emailErr.style.display = "block";
    }
    btnActHandler();
})

