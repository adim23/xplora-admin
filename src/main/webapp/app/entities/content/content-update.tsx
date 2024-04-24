import React, { useState, useEffect } from 'react';
import { Link, useNavigate, useParams } from 'react-router-dom';
import { Button, Row, Col, FormText } from 'reactstrap';
import { isNumber, Translate, translate, ValidatedField, ValidatedForm } from 'react-jhipster';
import { FontAwesomeIcon } from '@fortawesome/react-fontawesome';

import { convertDateTimeFromServer, convertDateTimeToServer, displayDefaultDateTime } from 'app/shared/util/date-utils';
import { mapIdList } from 'app/shared/util/entity-utils';
import { useAppDispatch, useAppSelector } from 'app/config/store';

import { ILanguage } from 'app/shared/model/language.model';
import { getEntities as getLanguages } from 'app/entities/language/language.reducer';
import { IUser } from 'app/shared/model/user.model';
import { getUsers } from 'app/modules/administration/user-management/user-management.reducer';
import { IDestination } from 'app/shared/model/destination.model';
import { getEntities as getDestinations } from 'app/entities/destination/destination.reducer';
import { ITourCategory } from 'app/shared/model/tour-category.model';
import { getEntities as getTourCategories } from 'app/entities/tour-category/tour-category.reducer';
import { IPlace } from 'app/shared/model/place.model';
import { getEntities as getPlaces } from 'app/entities/place/place.reducer';
import { IPlaceCategory } from 'app/shared/model/place-category.model';
import { getEntities as getPlaceCategories } from 'app/entities/place-category/place-category.reducer';
import { ITourExtraCategory } from 'app/shared/model/tour-extra-category.model';
import { getEntities as getTourExtraCategories } from 'app/entities/tour-extra-category/tour-extra-category.reducer';
import { ITourExtra } from 'app/shared/model/tour-extra.model';
import { getEntities as getTourExtras } from 'app/entities/tour-extra/tour-extra.reducer';
import { IMenu } from 'app/shared/model/menu.model';
import { getEntities as getMenus } from 'app/entities/menu/menu.reducer';
import { IWebPage } from 'app/shared/model/web-page.model';
import { getEntities as getWebPages } from 'app/entities/web-page/web-page.reducer';
import { ITag } from 'app/shared/model/tag.model';
import { getEntities as getTags } from 'app/entities/tag/tag.reducer';
import { ITourStep } from 'app/shared/model/tour-step.model';
import { getEntities as getTourSteps } from 'app/entities/tour-step/tour-step.reducer';
import { IPromotion } from 'app/shared/model/promotion.model';
import { getEntities as getPromotions } from 'app/entities/promotion/promotion.reducer';
import { IImageFile } from 'app/shared/model/image-file.model';
import { getEntities as getImageFiles } from 'app/entities/image-file/image-file.reducer';
import { IContent } from 'app/shared/model/content.model';
import { getEntity, updateEntity, createEntity, reset } from './content.reducer';

export const ContentUpdate = () => {
  const dispatch = useAppDispatch();

  const navigate = useNavigate();

  const { id } = useParams<'id'>();
  const isNew = id === undefined;

  const languages = useAppSelector(state => state.language.entities);
  const users = useAppSelector(state => state.userManagement.users);
  const destinations = useAppSelector(state => state.destination.entities);
  const tourCategories = useAppSelector(state => state.tourCategory.entities);
  const places = useAppSelector(state => state.place.entities);
  const placeCategories = useAppSelector(state => state.placeCategory.entities);
  const tourExtraCategories = useAppSelector(state => state.tourExtraCategory.entities);
  const tourExtras = useAppSelector(state => state.tourExtra.entities);
  const menus = useAppSelector(state => state.menu.entities);
  const webPages = useAppSelector(state => state.webPage.entities);
  const tags = useAppSelector(state => state.tag.entities);
  const tourSteps = useAppSelector(state => state.tourStep.entities);
  const promotions = useAppSelector(state => state.promotion.entities);
  const imageFiles = useAppSelector(state => state.imageFile.entities);
  const contentEntity = useAppSelector(state => state.content.entity);
  const loading = useAppSelector(state => state.content.loading);
  const updating = useAppSelector(state => state.content.updating);
  const updateSuccess = useAppSelector(state => state.content.updateSuccess);

  const handleClose = () => {
    navigate('/content' + location.search);
  };

  useEffect(() => {
    if (isNew) {
      dispatch(reset());
    } else {
      dispatch(getEntity(id));
    }

    dispatch(getLanguages({}));
    dispatch(getUsers({}));
    dispatch(getDestinations({}));
    dispatch(getTourCategories({}));
    dispatch(getPlaces({}));
    dispatch(getPlaceCategories({}));
    dispatch(getTourExtraCategories({}));
    dispatch(getTourExtras({}));
    dispatch(getMenus({}));
    dispatch(getWebPages({}));
    dispatch(getTags({}));
    dispatch(getTourSteps({}));
    dispatch(getPromotions({}));
    dispatch(getImageFiles({}));
  }, []);

  useEffect(() => {
    if (updateSuccess) {
      handleClose();
    }
  }, [updateSuccess]);

  // eslint-disable-next-line complexity
  const saveEntity = values => {
    if (values.id !== undefined && typeof values.id !== 'number') {
      values.id = Number(values.id);
    }

    const entity = {
      ...contentEntity,
      ...values,
      language: languages.find(it => it.id.toString() === values.language?.toString()),
      createdBy: users.find(it => it.id.toString() === values.createdBy?.toString()),
      destination: destinations.find(it => it.id.toString() === values.destination?.toString()),
      tourCategory: tourCategories.find(it => it.id.toString() === values.tourCategory?.toString()),
      place: places.find(it => it.id.toString() === values.place?.toString()),
      placeCategory: placeCategories.find(it => it.id.toString() === values.placeCategory?.toString()),
      tourExtraCategory: tourExtraCategories.find(it => it.id.toString() === values.tourExtraCategory?.toString()),
      tourExtra: tourExtras.find(it => it.id.toString() === values.tourExtra?.toString()),
      menu: menus.find(it => it.id.toString() === values.menu?.toString()),
      webPage: webPages.find(it => it.id.toString() === values.webPage?.toString()),
      tag: tags.find(it => it.id.toString() === values.tag?.toString()),
      tourStep: tourSteps.find(it => it.id.toString() === values.tourStep?.toString()),
      promotion: promotions.find(it => it.id.toString() === values.promotion?.toString()),
      imageFile: imageFiles.find(it => it.id.toString() === values.imageFile?.toString()),
    };

    if (isNew) {
      dispatch(createEntity(entity));
    } else {
      dispatch(updateEntity(entity));
    }
  };

  const defaultValues = () =>
    isNew
      ? {}
      : {
          ...contentEntity,
          language: contentEntity?.language?.id,
          createdBy: contentEntity?.createdBy?.id,
          destination: contentEntity?.destination?.id,
          tourCategory: contentEntity?.tourCategory?.id,
          place: contentEntity?.place?.id,
          placeCategory: contentEntity?.placeCategory?.id,
          tourExtraCategory: contentEntity?.tourExtraCategory?.id,
          tourExtra: contentEntity?.tourExtra?.id,
          menu: contentEntity?.menu?.id,
          webPage: contentEntity?.webPage?.id,
          tag: contentEntity?.tag?.id,
          tourStep: contentEntity?.tourStep?.id,
          promotion: contentEntity?.promotion?.id,
          imageFile: contentEntity?.imageFile?.id,
        };

  return (
    <div>
      <Row className="justify-content-center">
        <Col md="8">
          <h2 id="xploraAdminApp.content.home.createOrEditLabel" data-cy="ContentCreateUpdateHeading">
            <Translate contentKey="xploraAdminApp.content.home.createOrEditLabel">Create or edit a Content</Translate>
          </h2>
        </Col>
      </Row>
      <Row className="justify-content-center">
        <Col md="8">
          {loading ? (
            <p>Loading...</p>
          ) : (
            <ValidatedForm defaultValues={defaultValues()} onSubmit={saveEntity}>
              {!isNew ? (
                <ValidatedField
                  name="id"
                  required
                  readOnly
                  id="content-id"
                  label={translate('global.field.id')}
                  validate={{ required: true }}
                />
              ) : null}
              <ValidatedField
                label={translate('xploraAdminApp.content.code')}
                id="content-code"
                name="code"
                data-cy="code"
                type="text"
                validate={{
                  required: { value: true, message: translate('entity.validation.required') },
                }}
              />
              <ValidatedField
                label={translate('xploraAdminApp.content.title')}
                id="content-title"
                name="title"
                data-cy="title"
                type="text"
              />
              <ValidatedField
                label={translate('xploraAdminApp.content.shortDescription')}
                id="content-shortDescription"
                name="shortDescription"
                data-cy="shortDescription"
                type="textarea"
              />
              <ValidatedField
                label={translate('xploraAdminApp.content.data')}
                id="content-data"
                name="data"
                data-cy="data"
                type="textarea"
              />
              <ValidatedField
                label={translate('xploraAdminApp.content.meta')}
                id="content-meta"
                name="meta"
                data-cy="meta"
                type="textarea"
              />
              <ValidatedField
                label={translate('xploraAdminApp.content.createdDate')}
                id="content-createdDate"
                name="createdDate"
                data-cy="createdDate"
                type="date"
              />
              <ValidatedField
                id="content-language"
                name="language"
                data-cy="language"
                label={translate('xploraAdminApp.content.language')}
                type="select"
                required
              >
                <option value="" key="0" />
                {languages
                  ? languages.map(otherEntity => (
                      <option value={otherEntity.id} key={otherEntity.id}>
                        {otherEntity.code}
                      </option>
                    ))
                  : null}
              </ValidatedField>
              <FormText>
                <Translate contentKey="entity.validation.required">This field is required.</Translate>
              </FormText>
              <ValidatedField
                id="content-createdBy"
                name="createdBy"
                data-cy="createdBy"
                label={translate('xploraAdminApp.content.createdBy')}
                type="select"
              >
                <option value="" key="0" />
                {users
                  ? users.map(otherEntity => (
                      <option value={otherEntity.id} key={otherEntity.id}>
                        {otherEntity.login}
                      </option>
                    ))
                  : null}
              </ValidatedField>
              <ValidatedField
                id="content-destination"
                name="destination"
                data-cy="destination"
                label={translate('xploraAdminApp.content.destination')}
                type="select"
              >
                <option value="" key="0" />
                {destinations
                  ? destinations.map(otherEntity => (
                      <option value={otherEntity.id} key={otherEntity.id}>
                        {otherEntity.code}
                      </option>
                    ))
                  : null}
              </ValidatedField>
              <ValidatedField
                id="content-tourCategory"
                name="tourCategory"
                data-cy="tourCategory"
                label={translate('xploraAdminApp.content.tourCategory')}
                type="select"
              >
                <option value="" key="0" />
                {tourCategories
                  ? tourCategories.map(otherEntity => (
                      <option value={otherEntity.id} key={otherEntity.id}>
                        {otherEntity.code}
                      </option>
                    ))
                  : null}
              </ValidatedField>
              <ValidatedField
                id="content-place"
                name="place"
                data-cy="place"
                label={translate('xploraAdminApp.content.place')}
                type="select"
              >
                <option value="" key="0" />
                {places
                  ? places.map(otherEntity => (
                      <option value={otherEntity.id} key={otherEntity.id}>
                        {otherEntity.code}
                      </option>
                    ))
                  : null}
              </ValidatedField>
              <ValidatedField
                id="content-placeCategory"
                name="placeCategory"
                data-cy="placeCategory"
                label={translate('xploraAdminApp.content.placeCategory')}
                type="select"
              >
                <option value="" key="0" />
                {placeCategories
                  ? placeCategories.map(otherEntity => (
                      <option value={otherEntity.id} key={otherEntity.id}>
                        {otherEntity.code}
                      </option>
                    ))
                  : null}
              </ValidatedField>
              <ValidatedField
                id="content-tourExtraCategory"
                name="tourExtraCategory"
                data-cy="tourExtraCategory"
                label={translate('xploraAdminApp.content.tourExtraCategory')}
                type="select"
              >
                <option value="" key="0" />
                {tourExtraCategories
                  ? tourExtraCategories.map(otherEntity => (
                      <option value={otherEntity.id} key={otherEntity.id}>
                        {otherEntity.code}
                      </option>
                    ))
                  : null}
              </ValidatedField>
              <ValidatedField
                id="content-tourExtra"
                name="tourExtra"
                data-cy="tourExtra"
                label={translate('xploraAdminApp.content.tourExtra')}
                type="select"
              >
                <option value="" key="0" />
                {tourExtras
                  ? tourExtras.map(otherEntity => (
                      <option value={otherEntity.id} key={otherEntity.id}>
                        {otherEntity.code}
                      </option>
                    ))
                  : null}
              </ValidatedField>
              <ValidatedField id="content-menu" name="menu" data-cy="menu" label={translate('xploraAdminApp.content.menu')} type="select">
                <option value="" key="0" />
                {menus
                  ? menus.map(otherEntity => (
                      <option value={otherEntity.id} key={otherEntity.id}>
                        {otherEntity.code}
                      </option>
                    ))
                  : null}
              </ValidatedField>
              <ValidatedField
                id="content-webPage"
                name="webPage"
                data-cy="webPage"
                label={translate('xploraAdminApp.content.webPage')}
                type="select"
              >
                <option value="" key="0" />
                {webPages
                  ? webPages.map(otherEntity => (
                      <option value={otherEntity.id} key={otherEntity.id}>
                        {otherEntity.code}
                      </option>
                    ))
                  : null}
              </ValidatedField>
              <ValidatedField id="content-tag" name="tag" data-cy="tag" label={translate('xploraAdminApp.content.tag')} type="select">
                <option value="" key="0" />
                {tags
                  ? tags.map(otherEntity => (
                      <option value={otherEntity.id} key={otherEntity.id}>
                        {otherEntity.code}
                      </option>
                    ))
                  : null}
              </ValidatedField>
              <ValidatedField
                id="content-tourStep"
                name="tourStep"
                data-cy="tourStep"
                label={translate('xploraAdminApp.content.tourStep')}
                type="select"
              >
                <option value="" key="0" />
                {tourSteps
                  ? tourSteps.map(otherEntity => (
                      <option value={otherEntity.id} key={otherEntity.id}>
                        {otherEntity.code}
                      </option>
                    ))
                  : null}
              </ValidatedField>
              <ValidatedField
                id="content-promotion"
                name="promotion"
                data-cy="promotion"
                label={translate('xploraAdminApp.content.promotion')}
                type="select"
              >
                <option value="" key="0" />
                {promotions
                  ? promotions.map(otherEntity => (
                      <option value={otherEntity.id} key={otherEntity.id}>
                        {otherEntity.code}
                      </option>
                    ))
                  : null}
              </ValidatedField>
              <ValidatedField
                id="content-imageFile"
                name="imageFile"
                data-cy="imageFile"
                label={translate('xploraAdminApp.content.imageFile')}
                type="select"
              >
                <option value="" key="0" />
                {imageFiles
                  ? imageFiles.map(otherEntity => (
                      <option value={otherEntity.id} key={otherEntity.id}>
                        {otherEntity.code}
                      </option>
                    ))
                  : null}
              </ValidatedField>
              <Button tag={Link} id="cancel-save" data-cy="entityCreateCancelButton" to="/content" replace color="info">
                <FontAwesomeIcon icon="arrow-left" />
                &nbsp;
                <span className="d-none d-md-inline">
                  <Translate contentKey="entity.action.back">Back</Translate>
                </span>
              </Button>
              &nbsp;
              <Button color="primary" id="save-entity" data-cy="entityCreateSaveButton" type="submit" disabled={updating}>
                <FontAwesomeIcon icon="save" />
                &nbsp;
                <Translate contentKey="entity.action.save">Save</Translate>
              </Button>
            </ValidatedForm>
          )}
        </Col>
      </Row>
    </div>
  );
};

export default ContentUpdate;
