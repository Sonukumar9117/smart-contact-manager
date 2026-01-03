
const html=document.querySelector('html');
const toggleThemeBtn=document.getElementById('toggle-theme');


const getTheme=()=>{
    return localStorage.getItem("theme")??'light';
}

const setTheme=()=>{
    const currentTheme=getTheme();
    const newTheme=currentTheme==='dark'?'light':'dark';
    html.classList.remove(currentTheme);
    html.classList.add(newTheme);
    localStorage.setItem("theme",newTheme);
}

document.addEventListener("DOMContentLoaded",()=>{
    const currentTheme=getTheme();
    toggleThemeBtn.checked=currentTheme==='dark';
    html.classList.add(currentTheme);
});
toggleThemeBtn.addEventListener('change',setTheme);
console.log(html.classList);





