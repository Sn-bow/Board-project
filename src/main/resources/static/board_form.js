const subject = document.querySelector(".subject_input_style");
const content = document.querySelector(".content_input_style");
const confirmBtn = document.querySelector(".confirm_btn");


let url = window.location.pathname

let subjectState;
let contentState;


if(url == "/board/create") {
    subjectState = false;
    contentState = false;
} else {
    subjectState = true;
    contentState = true;
}
// signin form state

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
    confirmBtn.disabled = false;
    confirmBtnStyle();
  } else {
    confirmBtn.disabled = true;
    confirmDisableBtnStyle();
  }
};
confirmBtnActivateHandler();


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
