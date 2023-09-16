const subject = document.querySelector(".subject_input_style");
const content = document.querySelector(".content_input_style");
const confirmBtn = document.querySelector(".confirm_btn");

// signin form state
let subjectState = false;
let contentState = false;

// function

const confirmBtnStyle = () => {
  confirmBtn.addEventListener("mouseover", () => {
    confirmBtn.style.cursor = "pointer";
    confirmBtn.style.backgroundColor = "#a3a1a1";
    confirmBtn.style.color = "#e3dede";
    confirmBtn.style.transition = "all 0.3s ease-in-out";
  });
  confirmBtn.addEventListener("mouseout", () => {
    confirmBtn.style.cursor = "pointer";
    confirmBtn.style.backgroundColor = "white";
    confirmBtn.style.color = "#6e6e6e";
    confirmBtn.style.transition = "all 0.3s ease-in-out";
  });
};

const confirmDisableBtnStyle = () => {
  confirmBtn.addEventListener("mouseover", () => {
    confirmBtn.style.cursor = "default";
    confirmBtn.style.backgroundColor = "white";
    confirmBtn.style.color = "#6e6e6e";
    confirmBtn.style.transition = "all 0.3s ease-in-out";
  });
  confirmBtn.addEventListener("mouseout", () => {
    confirmBtn.style.cursor = "default";
    confirmBtn.style.backgroundColor = "white";
    confirmBtn.style.color = "#6e6e6e";
    confirmBtn.style.transition = "all 0.3s ease-in-out";
  });
};

const confirmBtnActivateHandler = () => {
  if (subjectState && contentState) {
    // 속성 disabled 추가 또는 삭제로 변경 되어야함
    confirmBtn.disabled = false;
    confirmBtnStyle();
  } else {
    confirmBtn.disabled = true;
    confirmDisableBtnStyle();
  }
};
confirmBtnActivateHandler();

// 정규식대로 아이디 입력이 안되었을 경우 와
// 비밀번호 입력이 안되었을 경우 로그인 버튼 비활성화
subject.addEventListener("keyup", (e) => {
  if (e.target.value != "") {
    subjectState = true;
  }
  if (e.target.value == "") {
    subjectState = false;
  }
  confirmBtnActivateHandler();
});

content.addEventListener("keyup", (e) => {
  if (e.target.value != "") {
    contentState = true;
  }
  if (e.target.value == "") {
    contentState = false;
  }
  confirmBtnActivateHandler();
});
