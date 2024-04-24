import destination from 'app/entities/destination/destination.reducer';
import placeCategory from 'app/entities/place-category/place-category.reducer';
import place from 'app/entities/place/place.reducer';
import tourCategory from 'app/entities/tour-category/tour-category.reducer';
import tour from 'app/entities/tour/tour.reducer';
import tourStep from 'app/entities/tour-step/tour-step.reducer';
import promotion from 'app/entities/promotion/promotion.reducer';
import tourSchedule from 'app/entities/tour-schedule/tour-schedule.reducer';
import tourExtraCategory from 'app/entities/tour-extra-category/tour-extra-category.reducer';
import tourExtra from 'app/entities/tour-extra/tour-extra.reducer';
import driver from 'app/entities/driver/driver.reducer';
import vehicle from 'app/entities/vehicle/vehicle.reducer';
import booking from 'app/entities/booking/booking.reducer';
import passenger from 'app/entities/passenger/passenger.reducer';
import imageFile from 'app/entities/image-file/image-file.reducer';
import webPage from 'app/entities/web-page/web-page.reducer';
import menu from 'app/entities/menu/menu.reducer';
import language from 'app/entities/language/language.reducer';
import content from 'app/entities/content/content.reducer';
import tag from 'app/entities/tag/tag.reducer';
import tourContent from 'app/entities/tour-content/tour-content.reducer';
import prompt from 'app/entities/prompt/prompt.reducer';
/* jhipster-needle-add-reducer-import - JHipster will add reducer here */

const entitiesReducers = {
  destination,
  placeCategory,
  place,
  tourCategory,
  tour,
  tourStep,
  promotion,
  tourSchedule,
  tourExtraCategory,
  tourExtra,
  driver,
  vehicle,
  booking,
  passenger,
  imageFile,
  webPage,
  menu,
  language,
  content,
  tag,
  tourContent,
  prompt,
  /* jhipster-needle-add-reducer-combine - JHipster will add reducer here */
};

export default entitiesReducers;
