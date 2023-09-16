const id = document.querySelector(".id");
const password = document.querySelector(".password");
const signInBtn = document.querySelector(".signin-submit");

// regExp
const pwdExp = /^(?=.*[a-zA-Z])(?=.*[!@#$%^*+=-])(?=.*[0-9]).{8,15}$/;

// signin form state
let idState = false;
let pwdState = false;

// function

const signInBtnStyle = () => {
  signInBtn.addEventListener("mouseover", () => {
    signInBtn.style.cursor = "pointer";
    signInBtn.style.backgroundColor = "#a3a1a1";
    signInBtn.style.color = "#e3dede";
    signInBtn.style.transition = "all 0.3s ease-in-out";
  });
  signInBtn.addEventListener("mouseout", () => {
    signInBtn.style.cursor = "pointer";
    signInBtn.style.backgroundColor = "white";
    signInBtn.style.color = "#6e6e6e";
    signInBtn.style.transition = "all 0.3s ease-in-out";
  });
};

const signInDisableBtnStyle = () => {
  signInBtn.addEventListener("mouseover", () => {
    signInBtn.style.cursor = "default";
    signInBtn.style.backgroundColor = "white";
    signInBtn.style.color = "#6e6e6e";
    signInBtn.style.transition = "all 0.3s ease-in-out";
  });
  signInBtn.addEventListener("mouseout", () => {
    signInBtn.style.cursor = "default";
    signInBtn.style.backgroundColor = "white";
    signInBtn.style.color = "#6e6e6e";
    signInBtn.style.transition = "all 0.3s ease-in-out";
  });
};

const signInBtnActivateHandler = () => {
  if (idState && pwdState) {
    // 속성 disabled 추가 또는 삭제로 변경 되어야함
    signInBtn.disabled = false;
    signInBtnStyle();
  } else {
    signInBtn.disabled = true;
    signInDisableBtnStyle();
  }
};
signInBtnActivateHandler();

// 정규식대로 아이디 입력이 안되었을 경우 와
// 비밀번호 입력이 안되었을 경우 로그인 버튼 비활성화
id.addEventListener("keyup", (e) => {
  if (e.target.value != "") {
    idState = true;
  }
  if (e.target.value == "") {
    idState = false;
  }
  signInBtnActivateHandler();
});

password.addEventListener("keyup", (e) => {
  if (pwdExp.test(e.target.value)) {
    pwdState = true;
  }
  if (!pwdExp.test(e.target.value)) {
    pwdState = false;
  }
  signInBtnActivateHandler();
});
