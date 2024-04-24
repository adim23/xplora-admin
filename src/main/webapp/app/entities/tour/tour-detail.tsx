import React, { useEffect } from 'react';
import { Link, useParams } from 'react-router-dom';
import { Button, Row, Col } from 'reactstrap';
import { Translate, openFile, byteSize, TextFormat } from 'react-jhipster';
import { FontAwesomeIcon } from '@fortawesome/react-fontawesome';

import { APP_DATE_FORMAT, APP_LOCAL_DATE_FORMAT } from 'app/config/constants';
import { DurationFormat } from 'app/shared/DurationFormat';
import { useAppDispatch, useAppSelector } from 'app/config/store';

import { getEntity } from './tour.reducer';

export const TourDetail = () => {
  const dispatch = useAppDispatch();

  const { id } = useParams<'id'>();

  useEffect(() => {
    dispatch(getEntity(id));
  }, []);

  const tourEntity = useAppSelector(state => state.tour.entity);
  return (
    <Row>
      <Col md="8">
        <h2 data-cy="tourDetailsHeading">
          <Translate contentKey="xploraAdminApp.tour.detail.title">Tour</Translate>
        </h2>
        <dl className="jh-entity-details">
          <dt>
            <span id="id">
              <Translate contentKey="global.field.id">ID</Translate>
            </span>
          </dt>
          <dd>{tourEntity.id}</dd>
          <dt>
            <span id="code">
              <Translate contentKey="xploraAdminApp.tour.code">Code</Translate>
            </span>
          </dt>
          <dd>{tourEntity.code}</dd>
          <dt>
            <span id="enabled">
              <Translate contentKey="xploraAdminApp.tour.enabled">Enabled</Translate>
            </span>
          </dt>
          <dd>{tourEntity.enabled ? 'true' : 'false'}</dd>
          <dt>
            <span id="kind">
              <Translate contentKey="xploraAdminApp.tour.kind">Kind</Translate>
            </span>
          </dt>
          <dd>{tourEntity.kind}</dd>
          <dt>
            <span id="mode">
              <Translate contentKey="xploraAdminApp.tour.mode">Mode</Translate>
            </span>
          </dt>
          <dd>{tourEntity.mode}</dd>
          <dt>
            <span id="icon">
              <Translate contentKey="xploraAdminApp.tour.icon">Icon</Translate>
            </span>
          </dt>
          <dd>{tourEntity.icon}</dd>
          <dt>
            <span id="duration">
              <Translate contentKey="xploraAdminApp.tour.duration">Duration</Translate>
            </span>
          </dt>
          <dd>{tourEntity.duration}</dd>
          <dt>
            <span id="durationMeasure">
              <Translate contentKey="xploraAdminApp.tour.durationMeasure">Duration Measure</Translate>
            </span>
          </dt>
          <dd>{tourEntity.durationMeasure}</dd>
          <dt>
            <span id="petFriendly">
              <Translate contentKey="xploraAdminApp.tour.petFriendly">Pet Friendly</Translate>
            </span>
          </dt>
          <dd>{tourEntity.petFriendly ? 'true' : 'false'}</dd>
          <dt>
            <span id="kidsAllowed">
              <Translate contentKey="xploraAdminApp.tour.kidsAllowed">Kids Allowed</Translate>
            </span>
          </dt>
          <dd>{tourEntity.kidsAllowed ? 'true' : 'false'}</dd>
          <dt>
            <span id="smoking">
              <Translate contentKey="xploraAdminApp.tour.smoking">Smoking</Translate>
            </span>
          </dt>
          <dd>{tourEntity.smoking ? 'true' : 'false'}</dd>
          <dt>
            <span id="availableFromDate">
              <Translate contentKey="xploraAdminApp.tour.availableFromDate">Available From Date</Translate>
            </span>
          </dt>
          <dd>
            {tourEntity.availableFromDate ? (
              <TextFormat value={tourEntity.availableFromDate} type="date" format={APP_LOCAL_DATE_FORMAT} />
            ) : null}
          </dd>
          <dt>
            <span id="availableToDate">
              <Translate contentKey="xploraAdminApp.tour.availableToDate">Available To Date</Translate>
            </span>
          </dt>
          <dd>
            {tourEntity.availableToDate ? (
              <TextFormat value={tourEntity.availableToDate} type="date" format={APP_LOCAL_DATE_FORMAT} />
            ) : null}
          </dd>
          <dt>
            <span id="initialPrice">
              <Translate contentKey="xploraAdminApp.tour.initialPrice">Initial Price</Translate>
            </span>
          </dt>
          <dd>{tourEntity.initialPrice}</dd>
          <dt>
            <span id="price">
              <Translate contentKey="xploraAdminApp.tour.price">Price</Translate>
            </span>
          </dt>
          <dd>{tourEntity.price}</dd>
          <dt>
            <span id="badge">
              <Translate contentKey="xploraAdminApp.tour.badge">Badge</Translate>
            </span>
          </dt>
          <dd>{tourEntity.badge}</dd>
          <dt>
            <span id="rating">
              <Translate contentKey="xploraAdminApp.tour.rating">Rating</Translate>
            </span>
          </dt>
          <dd>{tourEntity.rating}</dd>
          <dt>
            <span id="widgetId">
              <Translate contentKey="xploraAdminApp.tour.widgetId">Widget Id</Translate>
            </span>
          </dt>
          <dd>{tourEntity.widgetId}</dd>
          <dt>
            <span id="externalId">
              <Translate contentKey="xploraAdminApp.tour.externalId">External Id</Translate>
            </span>
          </dt>
          <dd>{tourEntity.externalId}</dd>
          <dt>
            <span id="createdDate">
              <Translate contentKey="xploraAdminApp.tour.createdDate">Created Date</Translate>
            </span>
          </dt>
          <dd>
            {tourEntity.createdDate ? <TextFormat value={tourEntity.createdDate} type="date" format={APP_LOCAL_DATE_FORMAT} /> : null}
          </dd>
          <dt>
            <span id="defaultImage">
              <Translate contentKey="xploraAdminApp.tour.defaultImage">Default Image</Translate>
            </span>
          </dt>
          <dd>{tourEntity.defaultImage}</dd>
          <dt>
            <span id="defaultImageData">
              <Translate contentKey="xploraAdminApp.tour.defaultImageData">Default Image Data</Translate>
            </span>
          </dt>
          <dd>
            {tourEntity.defaultImageData ? (
              <div>
                {tourEntity.defaultImageDataContentType ? (
                  <a onClick={openFile(tourEntity.defaultImageDataContentType, tourEntity.defaultImageData)}>
                    <img
                      src={`data:${tourEntity.defaultImageDataContentType};base64,${tourEntity.defaultImageData}`}
                      style={{ maxHeight: '30px' }}
                    />
                  </a>
                ) : null}
                <span>
                  {tourEntity.defaultImageDataContentType}, {byteSize(tourEntity.defaultImageData)}
                </span>
              </div>
            ) : null}
          </dd>
          <dt>
            <span id="accessibility">
              <Translate contentKey="xploraAdminApp.tour.accessibility">Accessibility</Translate>
            </span>
          </dt>
          <dd>{tourEntity.accessibility ? 'true' : 'false'}</dd>
          <dt>
            <span id="audioGuide">
              <Translate contentKey="xploraAdminApp.tour.audioGuide">Audio Guide</Translate>
            </span>
          </dt>
          <dd>{tourEntity.audioGuide ? 'true' : 'false'}</dd>
          <dt>
            <span id="tourGuide">
              <Translate contentKey="xploraAdminApp.tour.tourGuide">Tour Guide</Translate>
            </span>
          </dt>
          <dd>{tourEntity.tourGuide ? 'true' : 'false'}</dd>
          <dt>
            <span id="cssStyle">
              <Translate contentKey="xploraAdminApp.tour.cssStyle">Css Style</Translate>
            </span>
          </dt>
          <dd>{tourEntity.cssStyle}</dd>
          <dt>
            <span id="departure">
              <Translate contentKey="xploraAdminApp.tour.departure">Departure</Translate>
            </span>
          </dt>
          <dd>{tourEntity.departure ? <TextFormat value={tourEntity.departure} type="date" format={APP_LOCAL_DATE_FORMAT} /> : null}</dd>
          <dt>
            <span id="returnTime">
              <Translate contentKey="xploraAdminApp.tour.returnTime">Return Time</Translate>
            </span>
          </dt>
          <dd>{tourEntity.returnTime ? <TextFormat value={tourEntity.returnTime} type="date" format={APP_LOCAL_DATE_FORMAT} /> : null}</dd>
          <dt>
            <span id="testIn">
              <Translate contentKey="xploraAdminApp.tour.testIn">Test In</Translate>
            </span>
          </dt>
          <dd>{tourEntity.testIn ? <TextFormat value={tourEntity.testIn} type="date" format={APP_DATE_FORMAT} /> : null}</dd>
          <dt>
            <span id="testZ">
              <Translate contentKey="xploraAdminApp.tour.testZ">Test Z</Translate>
            </span>
          </dt>
          <dd>{tourEntity.testZ ? <TextFormat value={tourEntity.testZ} type="date" format={APP_DATE_FORMAT} /> : null}</dd>
          <dt>
            <span id="dur">
              <Translate contentKey="xploraAdminApp.tour.dur">Dur</Translate>
            </span>
          </dt>
          <dd>
            {tourEntity.dur ? <DurationFormat value={tourEntity.dur} /> : null} ({tourEntity.dur})
          </dd>
          <dt>
            <Translate contentKey="xploraAdminApp.tour.content">Content</Translate>
          </dt>
          <dd>{tourEntity.content ? tourEntity.content.code : ''}</dd>
          <dt>
            <Translate contentKey="xploraAdminApp.tour.createdBy">Created By</Translate>
          </dt>
          <dd>{tourEntity.createdBy ? tourEntity.createdBy.login : ''}</dd>
          <dt>
            <Translate contentKey="xploraAdminApp.tour.meetingPoint">Meeting Point</Translate>
          </dt>
          <dd>{tourEntity.meetingPoint ? tourEntity.meetingPoint.code : ''}</dd>
          <dt>
            <Translate contentKey="xploraAdminApp.tour.finishPoint">Finish Point</Translate>
          </dt>
          <dd>{tourEntity.finishPoint ? tourEntity.finishPoint.code : ''}</dd>
          <dt>
            <Translate contentKey="xploraAdminApp.tour.tourExtra">Tour Extra</Translate>
          </dt>
          <dd>
            {tourEntity.tourExtras
              ? tourEntity.tourExtras.map((val, i) => (
                  <span key={val.id}>
                    <a>{val.code}</a>
                    {tourEntity.tourExtras && i === tourEntity.tourExtras.length - 1 ? '' : ', '}
                  </span>
                ))
              : null}
          </dd>
          <dt>
            <Translate contentKey="xploraAdminApp.tour.tags">Tags</Translate>
          </dt>
          <dd>
            {tourEntity.tags
              ? tourEntity.tags.map((val, i) => (
                  <span key={val.id}>
                    <a>{val.code}</a>
                    {tourEntity.tags && i === tourEntity.tags.length - 1 ? '' : ', '}
                  </span>
                ))
              : null}
          </dd>
          <dt>
            <Translate contentKey="xploraAdminApp.tour.promotions">Promotions</Translate>
          </dt>
          <dd>
            {tourEntity.promotions
              ? tourEntity.promotions.map((val, i) => (
                  <span key={val.id}>
                    <a>{val.code}</a>
                    {tourEntity.promotions && i === tourEntity.promotions.length - 1 ? '' : ', '}
                  </span>
                ))
              : null}
          </dd>
          <dt>
            <Translate contentKey="xploraAdminApp.tour.category">Category</Translate>
          </dt>
          <dd>
            {tourEntity.categories
              ? tourEntity.categories.map((val, i) => (
                  <span key={val.id}>
                    <a>{val.code}</a>
                    {tourEntity.categories && i === tourEntity.categories.length - 1 ? '' : ', '}
                  </span>
                ))
              : null}
          </dd>
          <dt>
            <Translate contentKey="xploraAdminApp.tour.destination">Destination</Translate>
          </dt>
          <dd>{tourEntity.destination ? tourEntity.destination.code : ''}</dd>
          <dt>
            <Translate contentKey="xploraAdminApp.tour.defaultCategory">Default Category</Translate>
          </dt>
          <dd>{tourEntity.defaultCategory ? tourEntity.defaultCategory.code : ''}</dd>
        </dl>
        <Button tag={Link} to="/tour" replace color="info" data-cy="entityDetailsBackButton">
          <FontAwesomeIcon icon="arrow-left" />{' '}
          <span className="d-none d-md-inline">
            <Translate contentKey="entity.action.back">Back</Translate>
          </span>
        </Button>
        &nbsp;
        <Button tag={Link} to={`/tour/${tourEntity.id}/edit`} replace color="primary">
          <FontAwesomeIcon icon="pencil-alt" />{' '}
          <span className="d-none d-md-inline">
            <Translate contentKey="entity.action.edit">Edit</Translate>
          </span>
        </Button>
      </Col>
    </Row>
  );
};

export default TourDetail;
