function switchForms(activeForm, inactiveForm) {
    activeForm.classList.toggle("hide");
    inactiveForm.classList.toggle("hide");
}

const sign_button = document.getElementById("sign");
const log_button = document.getElementById("log");
const clickme_button = document.getElementById("clickme");
const sign = document.querySelector(".signup");
const log = document.querySelector(".login");
const typo = document.querySelector(".typo");

sign_button.addEventListener("click", () => {
    event.preventDefault();
    switchForms(sign, log);
    typo.classList.toggle("hide");
});

log_button.addEventListener("click", () => {
    event.preventDefault();
    switchForms(log, sign);
    typo.classList.toggle("hide");
});