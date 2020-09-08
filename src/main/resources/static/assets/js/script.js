
const toggle = document.querySelector('.toggler');
const menu = document.querySelector('.menu');


function toggler() {
    toggle.classList.toggle('closer');
    menu.classList.toggle('active');
}