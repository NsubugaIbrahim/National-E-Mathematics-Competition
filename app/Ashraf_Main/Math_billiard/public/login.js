
const login_container = document.getElementById('login_container');
const registerBtn = document.getElementById('register');
const loginBtn = document.getElementById('login');

registerBtn.addEventListener('click', () => {
    login_container.classList.add("active");
});

loginBtn.addEventListener('click', () => {
    login_container.classList.remove("active");
});

