import React, { useState, useEffect } from 'react';
import { Link, useNavigate, useParams } from 'react-router-dom';
import { Button, Row, Col, FormText } from 'reactstrap';
import { isNumber, Translate, translate, ValidatedField, ValidatedForm, ValidatedBlobField } from 'react-jhipster';
import { FontAwesomeIcon } from '@fortawesome/react-fontawesome';

import { convertDateTimeFromServer, convertDateTimeToServer, displayDefaultDateTime } from 'app/shared/util/date-utils';
import { mapIdList } from 'app/shared/util/entity-utils';
import { useAppDispatch, useAppSelector } from 'app/config/store';

import { ITourContent } from 'app/shared/model/tour-content.model';
import { getEntities as getTourContents } from 'app/entities/tour-content/tour-content.reducer';
import { IUser } from 'app/shared/model/user.model';
import { getUsers } from 'app/modules/administration/user-management/user-management.reducer';
import { IPlace } from 'app/shared/model/place.model';
import { getEntities as getPlaces } from 'app/entities/place/place.reducer';
import { ITourExtra } from 'app/shared/model/tour-extra.model';
import { getEntities as getTourExtras } from 'app/entities/tour-extra/tour-extra.reducer';
import { ITag } from 'app/shared/model/tag.model';
import { getEntities as getTags } from 'app/entities/tag/tag.reducer';
import { IPromotion } from 'app/shared/model/promotion.model';
import { getEntities as getPromotions } from 'app/entities/promotion/promotion.reducer';
import { ITourCategory } from 'app/shared/model/tour-category.model';
import { getEntities as getTourCategories } from 'app/entities/tour-category/tour-category.reducer';
import { IDestination } from 'app/shared/model/destination.model';
import { getEntities as getDestinations } from 'app/entities/destination/destination.reducer';
import { ITour } from 'app/shared/model/tour.model';
import { TourKind } from 'app/shared/model/enumerations/tour-kind.model';
import { TourMode } from 'app/shared/model/enumerations/tour-mode.model';
import { DurationMeasure } from 'app/shared/model/enumerations/duration-measure.model';
import { getEntity, updateEntity, createEntity, reset } from './tour.reducer';

export const TourUpdate = () => {
  const dispatch = useAppDispatch();

  const navigate = useNavigate();

  const { id } = useParams<'id'>();
  const isNew = id === undefined;

  const tourContents = useAppSelector(state => state.tourContent.entities);
  const users = useAppSelector(state => state.userManagement.users);
  const places = useAppSelector(state => state.place.entities);
  const tourExtras = useAppSelector(state => state.tourExtra.entities);
  const tags = useAppSelector(state => state.tag.entities);
  const promotions = useAppSelector(state => state.promotion.entities);
  const tourCategories = useAppSelector(state => state.tourCategory.entities);
  const destinations = useAppSelector(state => state.destination.entities);
  const tourEntity = useAppSelector(state => state.tour.entity);
  const loading = useAppSelector(state => state.tour.loading);
  const updating = useAppSelector(state => state.tour.updating);
  const updateSuccess = useAppSelector(state => state.tour.updateSuccess);
  const tourKindValues = Object.keys(TourKind);
  const tourModeValues = Object.keys(TourMode);
  const durationMeasureValues = Object.keys(DurationMeasure);

  const handleClose = () => {
    navigate('/tour' + location.search);
  };

  useEffect(() => {
    if (isNew) {
      dispatch(reset());
    } else {
      dispatch(getEntity(id));
    }

    dispatch(getTourContents({}));
    dispatch(getUsers({}));
    dispatch(getPlaces({}));
    dispatch(getTourExtras({}));
    dispatch(getTags({}));
    dispatch(getPromotions({}));
    dispatch(getTourCategories({}));
    dispatch(getDestinations({}));
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
    if (values.duration !== undefined && typeof values.duration !== 'number') {
      values.duration = Number(values.duration);
    }
    if (values.initialPrice !== undefined && typeof values.initialPrice !== 'number') {
      values.initialPrice = Number(values.initialPrice);
    }
    if (values.price !== undefined && typeof values.price !== 'number') {
      values.price = Number(values.price);
    }
    if (values.rating !== undefined && typeof values.rating !== 'number') {
      values.rating = Number(values.rating);
    }
    values.testIn = convertDateTimeToServer(values.testIn);
    values.testZ = convertDateTimeToServer(values.testZ);

    const entity = {
      ...tourEntity,
      ...values,
      content: tourContents.find(it => it.id.toString() === values.content?.toString()),
      createdBy: users.find(it => it.id.toString() === values.createdBy?.toString()),
      meetingPoint: places.find(it => it.id.toString() === values.meetingPoint?.toString()),
      finishPoint: places.find(it => it.id.toString() === values.finishPoint?.toString()),
      tourExtras: mapIdList(values.tourExtras),
      tags: mapIdList(values.tags),
      promotions: mapIdList(values.promotions),
      categories: mapIdList(values.categories),
      destination: destinations.find(it => it.id.toString() === values.destination?.toString()),
      defaultCategory: tourCategories.find(it => it.id.toString() === values.defaultCategory?.toString()),
    };

    if (isNew) {
      dispatch(createEntity(entity));
    } else {
      dispatch(updateEntity(entity));
    }
  };

  const defaultValues = () =>
    isNew
      ? {
          testIn: displayDefaultDateTime(),
          testZ: displayDefaultDateTime(),
        }
      : {
          kind: 'TOUR',
          mode: 'BUS',
          durationMeasure: 'MINUTES',
          ...tourEntity,
          testIn: convertDateTimeFromServer(tourEntity.testIn),
          testZ: convertDateTimeFromServer(tourEntity.testZ),
          content: tourEntity?.content?.id,
          createdBy: tourEntity?.createdBy?.id,
          meetingPoint: tourEntity?.meetingPoint?.id,
          finishPoint: tourEntity?.finishPoint?.id,
          tourExtras: tourEntity?.tourExtras?.map(e => e.id.toString()),
          tags: tourEntity?.tags?.map(e => e.id.toString()),
          promotions: tourEntity?.promotions?.map(e => e.id.toString()),
          categories: tourEntity?.categories?.map(e => e.id.toString()),
          destination: tourEntity?.destination?.id,
          defaultCategory: tourEntity?.defaultCategory?.id,
        };

  return (
    <div>
      <Row className="justify-content-center">
        <Col md="8">
          <h2 id="xploraAdminApp.tour.home.createOrEditLabel" data-cy="TourCreateUpdateHeading">
            <Translate contentKey="xploraAdminApp.tour.home.createOrEditLabel">Create or edit a Tour</Translate>
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
                  id="tour-id"
                  label={translate('global.field.id')}
                  validate={{ required: true }}
                />
              ) : null}
              <ValidatedField
                label={translate('xploraAdminApp.tour.code')}
                id="tour-code"
                name="code"
                data-cy="code"
                type="text"
                validate={{
                  required: { value: true, message: translate('entity.validation.required') },
                }}
              />
              <ValidatedField
                label={translate('xploraAdminApp.tour.enabled')}
                id="tour-enabled"
                name="enabled"
                data-cy="enabled"
                check
                type="checkbox"
              />
              <ValidatedField label={translate('xploraAdminApp.tour.kind')} id="tour-kind" name="kind" data-cy="kind" type="select">
                {tourKindValues.map(tourKind => (
                  <option value={tourKind} key={tourKind}>
                    {translate('xploraAdminApp.TourKind.' + tourKind)}
                  </option>
                ))}
              </ValidatedField>
              <ValidatedField label={translate('xploraAdminApp.tour.mode')} id="tour-mode" name="mode" data-cy="mode" type="select">
                {tourModeValues.map(tourMode => (
                  <option value={tourMode} key={tourMode}>
                    {translate('xploraAdminApp.TourMode.' + tourMode)}
                  </option>
                ))}
              </ValidatedField>
              <ValidatedField label={translate('xploraAdminApp.tour.icon')} id="tour-icon" name="icon" data-cy="icon" type="text" />
              <ValidatedField
                label={translate('xploraAdminApp.tour.duration')}
                id="tour-duration"
                name="duration"
                data-cy="duration"
                type="text"
                validate={{
                  required: { value: true, message: translate('entity.validation.required') },
                  validate: v => isNumber(v) || translate('entity.validation.number'),
                }}
              />
              <ValidatedField
                label={translate('xploraAdminApp.tour.durationMeasure')}
                id="tour-durationMeasure"
                name="durationMeasure"
                data-cy="durationMeasure"
                type="select"
              >
                {durationMeasureValues.map(durationMeasure => (
                  <option value={durationMeasure} key={durationMeasure}>
                    {translate('xploraAdminApp.DurationMeasure.' + durationMeasure)}
                  </option>
                ))}
              </ValidatedField>
              <ValidatedField
                label={translate('xploraAdminApp.tour.petFriendly')}
                id="tour-petFriendly"
                name="petFriendly"
                data-cy="petFriendly"
                check
                type="checkbox"
              />
              <ValidatedField
                label={translate('xploraAdminApp.tour.kidsAllowed')}
                id="tour-kidsAllowed"
                name="kidsAllowed"
                data-cy="kidsAllowed"
                check
                type="checkbox"
              />
              <ValidatedField
                label={translate('xploraAdminApp.tour.smoking')}
                id="tour-smoking"
                name="smoking"
                data-cy="smoking"
                check
                type="checkbox"
              />
              <ValidatedField
                label={translate('xploraAdminApp.tour.availableFromDate')}
                id="tour-availableFromDate"
                name="availableFromDate"
                data-cy="availableFromDate"
                type="date"
              />
              <ValidatedField
                label={translate('xploraAdminApp.tour.availableToDate')}
                id="tour-availableToDate"
                name="availableToDate"
                data-cy="availableToDate"
                type="date"
              />
              <ValidatedField
                label={translate('xploraAdminApp.tour.initialPrice')}
                id="tour-initialPrice"
                name="initialPrice"
                data-cy="initialPrice"
                type="text"
              />
              <ValidatedField label={translate('xploraAdminApp.tour.price')} id="tour-price" name="price" data-cy="price" type="text" />
              <ValidatedField label={translate('xploraAdminApp.tour.badge')} id="tour-badge" name="badge" data-cy="badge" type="text" />
              <ValidatedField label={translate('xploraAdminApp.tour.rating')} id="tour-rating" name="rating" data-cy="rating" type="text" />
              <ValidatedField
                label={translate('xploraAdminApp.tour.widgetId')}
                id="tour-widgetId"
                name="widgetId"
                data-cy="widgetId"
                type="text"
              />
              <ValidatedField
                label={translate('xploraAdminApp.tour.externalId')}
                id="tour-externalId"
                name="externalId"
                data-cy="externalId"
                type="text"
              />
              <ValidatedField
                label={translate('xploraAdminApp.tour.createdDate')}
                id="tour-createdDate"
                name="createdDate"
                data-cy="createdDate"
                type="date"
              />
              <ValidatedField
                label={translate('xploraAdminApp.tour.defaultImage')}
                id="tour-defaultImage"
                name="defaultImage"
                data-cy="defaultImage"
                type="text"
              />
              <ValidatedBlobField
                label={translate('xploraAdminApp.tour.defaultImageData')}
                id="tour-defaultImageData"
                name="defaultImageData"
                data-cy="defaultImageData"
                isImage
                accept="image/*"
              />
              <ValidatedField
                label={translate('xploraAdminApp.tour.accessibility')}
                id="tour-accessibility"
                name="accessibility"
                data-cy="accessibility"
                check
                type="checkbox"
              />
              <ValidatedField
                label={translate('xploraAdminApp.tour.audioGuide')}
                id="tour-audioGuide"
                name="audioGuide"
                data-cy="audioGuide"
                check
                type="checkbox"
              />
              <ValidatedField
                label={translate('xploraAdminApp.tour.tourGuide')}
                id="tour-tourGuide"
                name="tourGuide"
                data-cy="tourGuide"
                check
                type="checkbox"
              />
              <ValidatedField
                label={translate('xploraAdminApp.tour.cssStyle')}
                id="tour-cssStyle"
                name="cssStyle"
                data-cy="cssStyle"
                type="textarea"
              />
              <ValidatedField
                label={translate('xploraAdminApp.tour.departure')}
                id="tour-departure"
                name="departure"
                data-cy="departure"
                type="date"
              />
              <ValidatedField
                label={translate('xploraAdminApp.tour.returnTime')}
                id="tour-returnTime"
                name="returnTime"
                data-cy="returnTime"
                type="date"
              />
              <ValidatedField
                label={translate('xploraAdminApp.tour.testIn')}
                id="tour-testIn"
                name="testIn"
                data-cy="testIn"
                type="datetime-local"
                placeholder="YYYY-MM-DD HH:mm"
              />
              <ValidatedField
                label={translate('xploraAdminApp.tour.testZ')}
                id="tour-testZ"
                name="testZ"
                data-cy="testZ"
                type="datetime-local"
                placeholder="YYYY-MM-DD HH:mm"
              />
              <ValidatedField label={translate('xploraAdminApp.tour.dur')} id="tour-dur" name="dur" data-cy="dur" type="text" />
              <ValidatedField
                id="tour-content"
                name="content"
                data-cy="content"
                label={translate('xploraAdminApp.tour.content')}
                type="select"
              >
                <option value="" key="0" />
                {tourContents
                  ? tourContents.map(otherEntity => (
                      <option value={otherEntity.id} key={otherEntity.id}>
                        {otherEntity.code}
                      </option>
                    ))
                  : null}
              </ValidatedField>
              <ValidatedField
                id="tour-createdBy"
                name="createdBy"
                data-cy="createdBy"
                label={translate('xploraAdminApp.tour.createdBy')}
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
                id="tour-meetingPoint"
                name="meetingPoint"
                data-cy="meetingPoint"
                label={translate('xploraAdminApp.tour.meetingPoint')}
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
                id="tour-finishPoint"
                name="finishPoint"
                data-cy="finishPoint"
                label={translate('xploraAdminApp.tour.finishPoint')}
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
                label={translate('xploraAdminApp.tour.tourExtra')}
                id="tour-tourExtra"
                data-cy="tourExtra"
                type="select"
                multiple
                name="tourExtras"
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
              <ValidatedField
                label={translate('xploraAdminApp.tour.tags')}
                id="tour-tags"
                data-cy="tags"
                type="select"
                multiple
                name="tags"
              >
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
                label={translate('xploraAdminApp.tour.promotions')}
                id="tour-promotions"
                data-cy="promotions"
                type="select"
                multiple
                name="promotions"
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
                label={translate('xploraAdminApp.tour.category')}
                id="tour-category"
                data-cy="category"
                type="select"
                multiple
                name="categories"
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
                id="tour-destination"
                name="destination"
                data-cy="destination"
                label={translate('xploraAdminApp.tour.destination')}
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
                id="tour-defaultCategory"
                name="defaultCategory"
                data-cy="defaultCategory"
                label={translate('xploraAdminApp.tour.defaultCategory')}
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
              <Button tag={Link} id="cancel-save" data-cy="entityCreateCancelButton" to="/tour" replace color="info">
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

export default TourUpdate;
