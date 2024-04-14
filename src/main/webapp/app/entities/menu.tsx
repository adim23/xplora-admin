import React from 'react';
import { Translate } from 'react-jhipster';

import MenuItem from 'app/shared/layout/menus/menu-item';

const EntitiesMenu = () => {
  return (
    <>
      {/* prettier-ignore */}
      <MenuItem icon="asterisk" to="/destination">
        <Translate contentKey="global.menu.entities.destination" />
      </MenuItem>
      <MenuItem icon="asterisk" to="/place-category">
        <Translate contentKey="global.menu.entities.placeCategory" />
      </MenuItem>
      <MenuItem icon="asterisk" to="/place">
        <Translate contentKey="global.menu.entities.place" />
      </MenuItem>
      <MenuItem icon="asterisk" to="/tour-category">
        <Translate contentKey="global.menu.entities.tourCategory" />
      </MenuItem>
      <MenuItem icon="asterisk" to="/tour">
        <Translate contentKey="global.menu.entities.tour" />
      </MenuItem>
      <MenuItem icon="asterisk" to="/tour-step">
        <Translate contentKey="global.menu.entities.tourStep" />
      </MenuItem>
      <MenuItem icon="asterisk" to="/promotion">
        <Translate contentKey="global.menu.entities.promotion" />
      </MenuItem>
      <MenuItem icon="asterisk" to="/tour-schedule">
        <Translate contentKey="global.menu.entities.tourSchedule" />
      </MenuItem>
      <MenuItem icon="asterisk" to="/tour-extra-category">
        <Translate contentKey="global.menu.entities.tourExtraCategory" />
      </MenuItem>
      <MenuItem icon="asterisk" to="/tour-extra">
        <Translate contentKey="global.menu.entities.tourExtra" />
      </MenuItem>
      <MenuItem icon="asterisk" to="/driver">
        <Translate contentKey="global.menu.entities.driver" />
      </MenuItem>
      <MenuItem icon="asterisk" to="/vehicle">
        <Translate contentKey="global.menu.entities.vehicle" />
      </MenuItem>
      <MenuItem icon="asterisk" to="/booking">
        <Translate contentKey="global.menu.entities.booking" />
      </MenuItem>
      <MenuItem icon="asterisk" to="/passenger">
        <Translate contentKey="global.menu.entities.passenger" />
      </MenuItem>
      <MenuItem icon="asterisk" to="/image-file">
        <Translate contentKey="global.menu.entities.imageFile" />
      </MenuItem>
      <MenuItem icon="asterisk" to="/web-page">
        <Translate contentKey="global.menu.entities.webPage" />
      </MenuItem>
      <MenuItem icon="asterisk" to="/menu">
        <Translate contentKey="global.menu.entities.menu" />
      </MenuItem>
      <MenuItem icon="asterisk" to="/language">
        <Translate contentKey="global.menu.entities.language" />
      </MenuItem>
      <MenuItem icon="asterisk" to="/content">
        <Translate contentKey="global.menu.entities.content" />
      </MenuItem>
      <MenuItem icon="asterisk" to="/tag">
        <Translate contentKey="global.menu.entities.tag" />
      </MenuItem>
      {/* jhipster-needle-add-entity-to-menu - JHipster will add entities to the menu here */}
    </>
  );
};

export default EntitiesMenu;
