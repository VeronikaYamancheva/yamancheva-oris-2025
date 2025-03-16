//SLIDER

const slider = document.querySelector('.slider');

const prevBtn = document.querySelector('.prev-button');
const nextBtn = document.querySelector('.next-button');

const slides = Array.from(slider.querySelectorAll('img'));
const slideCount = slides.length;
let currentIndex = 0;

prevBtn.addEventListener('click', showPrevious);
nextBtn.addEventListener('click', showNext);

function showPrevious() {
    currentIndex = (currentIndex - 1 + slideCount) % slideCount;
    updateSlider();
}

function showNext() {
    currentIndex = (currentIndex + 1) % slideCount;
    updateSlider();
}

function updateSlider() {
    slides.forEach((slide, index) => {
        if (index === currentIndex) {
            slide.style.display = 'block';
        } else {
            slide.style.display = 'none';
        }
    });
}
updateSlider();

//GOOGLE MAP

